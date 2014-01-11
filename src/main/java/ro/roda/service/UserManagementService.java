package ro.roda.service;

import java.util.List;

import ro.roda.transformer.UserGroupInfo;
import ro.roda.transformer.UserGroupList;
import ro.roda.transformer.UserList;
import ro.roda.transformer.UsersByGroup;

public interface UserManagementService {

	public abstract List<UserGroupList> findAllUserGroupLists();

	public abstract UserGroupInfo findUserGroupInfo(Integer id);

	public abstract List<UserList> findAllUserLists();

	public abstract List<UsersByGroup> findUsersByGroup(Integer id);
}
