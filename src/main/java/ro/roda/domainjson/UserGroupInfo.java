package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.UserGroup;
import flexjson.JSONSerializer;

@Configurable
public class UserGroupInfo extends UserGroupList {

	public static String toJsonArray(Collection<UserGroupInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name", "enabled", "description", "nrusers");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<UserGroupInfo> findAllUserGroupInfos() {
		List<UserGroupInfo> result = new ArrayList<UserGroupInfo>();

		List<UserGroup> userGroups = UserGroup.findAllUserGroups();

		if (userGroups != null && userGroups.size() > 0) {

			Iterator<UserGroup> userGroupsIterator = userGroups.iterator();
			while (userGroupsIterator.hasNext()) {
				UserGroup userGroup = (UserGroup) userGroupsIterator.next();
				result.add(new UserGroupInfo(userGroup));
			}
		}

		return result;
	}

	public static UserGroupInfo findUserGroupInfo(Integer id) {
		UserGroup userGroup = UserGroup.findUserGroup(id);

		if (userGroup != null) {
			return new UserGroupInfo(userGroup);
		}
		return null;
	}

	public UserGroupInfo(Integer id, String name, String description, boolean enabled, Integer nrusers) {
		super(id, name, description, enabled, nrusers);
	}

	public UserGroupInfo(UserGroup userGroup) {
		this(userGroup.getId(), userGroup.getGroupname(), userGroup.getDescription(), userGroup.isEnabled(), userGroup
				.getAuthorities() != null ? userGroup.getAuthorities().size() : 0);
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name", "enabled", "description", "nrusers");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

}
