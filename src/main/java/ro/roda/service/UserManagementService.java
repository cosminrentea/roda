package ro.roda.service;

import java.util.Date;
import java.util.List;

import ro.roda.domainjson.AdminJson;
import ro.roda.domainjson.UserActivities;
import ro.roda.domainjson.UserGroupInfo;
import ro.roda.domainjson.UserGroupList;
import ro.roda.domainjson.UserList;
import ro.roda.domainjson.UserMessages;
import ro.roda.domainjson.UsersByGroup;

public interface UserManagementService {

	// reader methods (GETs)

	public abstract List<UserGroupList> findAllUserGroupLists();

	public abstract UserGroupInfo findUserGroupInfo(Integer id);

	public abstract List<UserList> findAllUserLists();

	public abstract List<UsersByGroup> findUsersByGroup(Integer id);

	public abstract List<UserMessages> findUserMessages(Integer id);

	public abstract List<UserActivities> findUserActivities(Integer id);

	// editing methods (POSTs)

	public abstract AdminJson userCreate(String authorityName, String username, String password, String passwordCheck,
			Boolean enabled, String email, String firstname, String middlename, String lastname, String title,
			String sex, String salutation, Date birthdate, String address1, String address2);

	public abstract AdminJson userSave(Integer userId, String username, String email, String firstname,
			String middlename, String lastname, String title, String sex, String salutation, Date birthdate,
			String address1, String address2);

	public abstract AdminJson userAddAuthority(Integer userId, String authorityName);

	public abstract AdminJson userRemoveAuthority(Integer userId, String authorityName);

	public abstract AdminJson userEnable(Integer userId);

	public abstract AdminJson userDisable(Integer userId);

	public abstract AdminJson userDrop(Integer userId);

	public abstract AdminJson userChangePassword(Integer userId, String password, String passwordCheck);

	public abstract AdminJson userMessage(Integer userId, String subject, String message);

	public abstract AdminJson groupMessage(String authorityName, String subject, String message);

}
