package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.UserGroup;
import flexjson.JSONSerializer;

@Configurable
public class UserGroupList extends UserGroupInfo {

	public static String toJsonArray(Collection<UserGroupList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name", "description", "enabled", "nrusers");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<UserGroupList> findAllUserGroupLists() {
		List<UserGroupList> result = new ArrayList<UserGroupList>();

		List<UserGroup> userGroups = UserGroup.findAllUserGroups();

		if (userGroups != null && userGroups.size() > 0) {

			Iterator<UserGroup> userGroupsIterator = userGroups.iterator();
			while (userGroupsIterator.hasNext()) {
				UserGroup userGroup = (UserGroup) userGroupsIterator.next();
				result.add(new UserGroupList(userGroup));
			}
		}

		return result;
	}

	public static UserGroupList findUserGroupInfo(Integer id) {
		UserGroup userGroup = UserGroup.findUserGroup(id);

		if (userGroup != null) {
			return new UserGroupList(userGroup);
		}
		return null;
	}

	private boolean enabled;

	public UserGroupList(Integer id, String name, String description, boolean enabled, Integer nrusers) {
		super(id, name, description, nrusers);
		this.enabled = enabled;
	}

	public UserGroupList(UserGroup userGroup) {
		this(userGroup.getId(), userGroup.getName(), userGroup.getDescription(), userGroup.isEnabled(), userGroup
				.getUserGroupUsers() != null ? userGroup.getUserGroupUsers().size() : 0);
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name", "description", "enabled", "nrusers");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

}
