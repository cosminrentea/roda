package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.AdminJson;
import ro.roda.domainjson.UserActivities;
import ro.roda.domainjson.UserGroupInfo;
import ro.roda.domainjson.UserGroupList;
import ro.roda.domainjson.UserList;
import ro.roda.domainjson.UserMessages;
import ro.roda.domainjson.UsersByGroup;

public interface UserManagementService {

	public abstract List<UserGroupList> findAllUserGroupLists();

	public abstract UserGroupInfo findUserGroupInfo(Integer id);

	public abstract List<UserList> findAllUserLists();

	public abstract List<UsersByGroup> findUsersByGroup(Integer id);

	public abstract List<UserMessages> findUserMessages(Integer id);

	public abstract List<UserActivities> findUserActivities(Integer id);

	public abstract AdminJson userSave(Integer id, String username, String password, String passwordCheck,
			String email, Boolean enabled);

	public abstract AdminJson groupSave(Integer id, String name, String description, Boolean enabled);

	public abstract AdminJson userAddToGroup(Integer userId, Integer groupId);

	public abstract AdminJson userRemoveFromGroup(Integer userId, Integer groupId);

	public abstract AdminJson userEnable(Integer userId);

	public abstract AdminJson userDisable(Integer userId);

	public abstract AdminJson userDrop(Integer userId);

	public abstract AdminJson userChangePassword(Integer userId, String password, String controlPassword);

	public abstract AdminJson userMessage(Integer userId, String subject, String message);

	public abstract AdminJson groupMessage(Integer groupId, String subject, String message);

}
