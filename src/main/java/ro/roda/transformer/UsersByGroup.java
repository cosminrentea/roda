package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.UserGroup;
import ro.roda.domain.UserGroupUser;
import ro.roda.domain.Users;
import flexjson.JSONSerializer;

@Configurable
public class UsersByGroup extends UserList {

	public static String toJsonArray(Collection<UsersByGroup> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "firstname", "lastname");
		serializer.include("id", "name", "email", "enabled");

		serializer.transform(new FieldNameTransformer("username"), "name");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<UsersByGroup> findAllUsersByGroups() {
		List<UsersByGroup> result = new ArrayList<UsersByGroup>();

		// TODO, if needed

		return result;
	}

	public static List<UsersByGroup> findUsersByGroup(Integer id) {
		List<UsersByGroup> result = null;

		UserGroup userGroup = UserGroup.findUserGroup(id);

		if (userGroup != null) {
			result = new ArrayList<UsersByGroup>();
			Set<UserGroupUser> userGroupUsers = userGroup.getUserGroupUsers();

			if (userGroupUsers != null && userGroupUsers.size() > 0) {
				Iterator<UserGroupUser> usersIterator = userGroupUsers.iterator();
				while (usersIterator.hasNext()) {
					Users user = usersIterator.next().getUserId();
					// TODO: what is the email associated to a username???
					result.add(new UsersByGroup(user));
				}
			}
		}

		return result;
	}

	public UsersByGroup(Users user) {
		super(user, null);
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "firstname", "lastname");
		serializer.include("id", "name", "email", "enabled");

		serializer.transform(new FieldNameTransformer("username"), "name");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
