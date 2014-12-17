package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Person;
import ro.roda.domain.Users;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class UserList extends JsonInfo {

	public static String toJsonArray(Collection<UserList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name", "firstname", "lastname", "middlename", "email", "enabled");
		serializer.include("title", "salutation", "image", "address1", "address2", "city", "country", "phone", "sex",
				"birthdate");

		serializer.transform(DATE_TRANSFORMER2, Date.class);
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

			result.add(new UserList(user));

			// TODO: decide if the relation with PersonLinks is still
			// interesting; if yes, delete the line above; else, delete the
			// commented code:
			// Set<PersonLinks> personLinks = user.getPersonLinkss();
			// if (personLinks != null && personLinks.size() > 0) {
			// // TODO a user should have only one PersonLink in the database,
			// // too
			// PersonLinks personLink = personLinks.iterator().next();
			// Person person = personLink.getPersonId();
			// result.add(new UserList(user, person));
			// } else {
			// result.add(new UserList(user));
			// }
			return result;
		}
		return null;
	}

	private String firstname;

	private String lastname;

	private String email;

	private boolean enabled;

	private String salutation;

	private String middlename;

	private String title;

	private String image;

	private String address1;

	private String address2;

	private String city;

	private String country;

	private String phone;

	private String sex;

	private Date birthdate;

	public UserList(Integer id, String username, String firstname, String lastname, String email, boolean enabled) {
		this.setId(id);
		this.setName(username);
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.enabled = enabled;
	}

	public UserList(Users user, Person person) {

		// TODO: get the main email, instead of the first one
		this(user.getId(), user.getUsername(), person != null ? person.getFname() : null, person != null ? person
				.getLname() : null,
				(person != null && person.getEmails() != null && person.getEmails().size() > 0) ? person.getEmails()
						.iterator().next().getEmail() : null, user.isEnabled());
	}

	public UserList(String firstname, String lastname, String middlename, String title, String salutation,
			String image, String address1, String address2, String city, String country, String phone, String sex,
			Date birthdate) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.middlename = middlename;
		this.title = title;
		this.salutation = salutation;
		this.image = image;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.sex = sex;
		this.birthdate = birthdate;
	}

	public UserList(Users user) {
		this(user.getFirstname(), user.getLastname(), user.getMiddlename(), user.getTitle(), user.getSalutation(), user
				.getImage(), user.getAddress1(), user.getAddress2(), user.getCity(), user.getCountry(),
				user.getPhone(), user.getSex(), user.getBirthdate());
		setId(user.getId());
		setName(user.getUsername());
		setEnabled(user.isEnabled());
		setEmail(user.getEmail());
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

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
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

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name", "firstname", "lastname", "middlename", "email", "enabled");
		serializer.include("title", "salutation", "image", "address1", "address2", "city", "country", "phone", "sex",
				"birthdate");

		serializer.transform(DATE_TRANSFORMER2, Date.class);
		serializer.transform(new FieldNameTransformer("username"), "name");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

}
