package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Person;
import ro.roda.domain.PersonLinks;
import ro.roda.domain.UserProfile;
import ro.roda.domain.Users;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class UserList extends JsonInfo {

	public static String toJsonArray(Collection<UserList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name", "firstname", "lastname", "email", "enabled");

		serializer.transform(new FieldNameTransformer("username"), "name");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<UserList> findAllUserLists() {
		List<UserList> result = new ArrayList<UserList>();

		List<Users> users = Users.findAllUserses();

		if (users != null && users.size() > 0) {

			Iterator<Users> usersIterator = users.iterator();
			while (usersIterator.hasNext()) {
				Users user = (Users) usersIterator.next();
				List<UserList> userList = findUserList(user.getId());
				result.addAll(userList);
			}
		}

		return result;
	}

	public static List<UserList> findUserList(Integer id) {
		Users user = Users.findUsers(id);

		List<UserList> result = new ArrayList<UserList>();

		if (user != null) {
			Set<PersonLinks> personLinks = user.getPersonLinkss();
			if (personLinks != null && personLinks.size() > 0) {
				// TODO a user should have only one PersonLink in the database,
				// too
				PersonLinks personLink = personLinks.iterator().next();
				Person person = personLink.getPersonId();
				result.add(new UserList(user, person));
			} else {
				// use the profile information
				UserProfile userProfile = UserProfile.findUserProfile(user.getId());
				result.add(new UserList(user, userProfile));
			}
			return result;
		}
		return null;
	}

	private String firstname;

	private String lastname;

	private String email;

	private boolean enabled;

	public UserList(Integer id, String username, String firstname, String lastname, String email, boolean enabled) {
		this.setId(id);
		this.setName(username);
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.enabled = enabled;
	}

	public UserList(Users user, Person person) {

		// TODO: get the main mail, instead of the first one
		this(user.getId(), user.getUsername(), person != null ? person.getFname() : null, person != null ? person
				.getLname() : null, (person != null && person.getPersonEmails() != null && person.getPersonEmails()
				.size() > 0) ? person.getPersonEmails().iterator().next().getEmailId().getEmail() : null, user
				.isEnabled());
	}

	public UserList(Users user, UserProfile userProfile) {

		// TODO: email for UserProfile
		this(user.getId(), user.getUsername(), userProfile != null ? userProfile.getFirstname() : null,
				userProfile != null ? userProfile.getLastname() : null, null, user.isEnabled());
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name", "firstname", "lastname", "email", "enabled");

		serializer.transform(new FieldNameTransformer("username"), "name");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

}
