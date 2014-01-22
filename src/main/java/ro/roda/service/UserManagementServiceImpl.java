package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.transformer.UserActivities;
import ro.roda.transformer.UserGroupInfo;
import ro.roda.transformer.UserGroupList;
import ro.roda.transformer.UserList;
import ro.roda.transformer.UserMessages;
import ro.roda.transformer.UsersByGroup;

@Service
@Transactional
public class UserManagementServiceImpl implements UserManagementService {

	public List<UserGroupList> findAllUserGroupLists() {
		return UserGroupList.findAllUserGroupLists();
	}

	public UserGroupInfo findUserGroupInfo(Integer id) {
		return UserGroupInfo.findUserGroupInfo(id);
	}

	public List<UserList> findAllUserLists() {
		return UserList.findAllUserLists();
	}

	public List<UsersByGroup> findUsersByGroup(Integer id) {
		return UsersByGroup.findUsersByGroup(id);
	}

	public List<UserMessages> findUserMessages(Integer id) {
		return UserMessages.findUserMessages(id);
	}

	public List<UserActivities> findUserActivities(Integer id) {
		return UserActivities.findUserActivities(id);
	}
}
