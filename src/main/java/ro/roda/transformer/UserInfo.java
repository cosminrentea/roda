package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsLayout;
import ro.roda.domain.CmsLayoutGroup;
import ro.roda.domain.CmsPage;
import ro.roda.domain.Person;
import ro.roda.domain.PersonLinks;
import ro.roda.domain.UserMessage;
import ro.roda.domain.UserProfile;
import ro.roda.domain.Users;
import flexjson.JSONSerializer;

@Configurable
public class UserInfo extends UserList {

	public static String toJsonArr(Collection<UserInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "id", "type", "firstname", "lastname", "profile.name", "profile.id",
				"profile.type");
		serializer.include("name", "email", "enabled", "profile", "messages");

		serializer.transform(new FieldNameTransformer("username"), "name");
		serializer.transform(DATE_TRANSFORMER2, Date.class);

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<UserInfo> findAllUserInfos() {
		List<UserInfo> result = new ArrayList<UserInfo>();

		List<Users> users = Users.findAllUserses();

		if (users != null && users.size() > 0) {

			Iterator<Users> usersIterator = users.iterator();
			while (usersIterator.hasNext()) {
				Users user = (Users) usersIterator.next();

				UserInfo userInfo;
				Set<PersonLinks> personLinks = user.getPersonLinkss();
				if (personLinks != null && personLinks.size() > 0) {
					userInfo = new UserInfo(user, personLinks.iterator().next().getPersonId());
				} else {
					userInfo = new UserInfo(user, null);
				}

				UserProfile profile = user.getUserProfile();
				Set<UserMessage> userMessages = new HashSet<UserMessage>();

				if (profile != null) {
					userInfo.setProfile(new UserProfileInfo(profile));
				}
				userInfo.setMessages(userMessages);

				result.add(userInfo);

			}
		}

		return result;
	}

	public static UserInfo findUserInfo(Integer id) {
		UserInfo result = null;

		Users user = Users.findUsers(id);

		if (user != null) {
			Set<PersonLinks> personLinks = user.getPersonLinkss();
			if (personLinks != null && personLinks.size() > 0) {
				result = new UserInfo(user, personLinks.iterator().next().getPersonId());
			} else {
				result = new UserInfo(user, null);
			}

			UserProfile profile = user.getUserProfile();
			Set<UserMessage> userMessages = new HashSet<UserMessage>();

			if (profile != null) {
				result.setProfile(new UserProfileInfo(profile));
			}
			result.setMessages(userMessages);
		}

		return result;
	}

	public static Set<CmsPage> getLayoutGroupPages(CmsLayoutGroup layoutGroup) {
		Set<CmsPage> pages = new HashSet<CmsPage>();

		Iterator<CmsLayout> layoutsIterator = layoutGroup.getCmsLayouts().iterator();

		while (layoutsIterator.hasNext()) {
			pages.addAll(layoutsIterator.next().getCmsPages());
		}

		Iterator<CmsLayoutGroup> layoutGroupsIterator = layoutGroup.getCmsLayoutGroups().iterator();

		while (layoutGroupsIterator.hasNext()) {
			pages.addAll(getLayoutGroupPages(layoutGroupsIterator.next()));
		}

		return pages;
	}

	private Set<UserGroupInfo> groups;

	private UserProfileInfo profile;

	private Set<UserMessage> messages;

	public UserInfo(Integer id, String username, String email, boolean enabled, Set<UserGroupInfo> groups,
			UserProfileInfo profile, Set<UserMessage> messages) {
		super(id, username, null, null, email, enabled);
		this.groups = groups;
		this.profile = profile;
		this.messages = messages;
	}

	public UserInfo(Users user, Person person) {
		// TODO: get the main mail, instead of the first one
		super(user.getId(), user.getUsername(), person != null ? person.getFname() : null, person != null ? person
				.getLname() : null, (person != null && person.getPersonEmails() != null && person.getPersonEmails()
				.size() > 0) ? person.getPersonEmails().iterator().next().getEmailId().getEmail() : null, user
				.isEnabled());
	}

	public Set<UserGroupInfo> getGroups() {
		return groups;
	}

	public UserProfileInfo getProfile() {
		return profile;
	}

	public Set<UserMessage> getMessages() {
		return messages;
	}

	public void setGroups(Set<UserGroupInfo> groups) {
		this.groups = groups;
	}

	public void setProfile(UserProfileInfo userProfile) {
		this.profile = userProfile;
	}

	public void setMessages(Set<UserMessage> messages) {
		this.messages = messages;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "id", "type", "firstname", "lastname", "profile.name", "profile.id",
				"profile.type");
		serializer.include("name", "email", "enabled", "profile", "messages");

		serializer.transform(new FieldNameTransformer("username"), "name");
		serializer.transform(DATE_TRANSFORMER2, Date.class);

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
