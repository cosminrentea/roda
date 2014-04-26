package ro.roda.service;

import java.util.List;

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
}
