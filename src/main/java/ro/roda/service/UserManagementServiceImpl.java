package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.Authorities;
import ro.roda.domain.AuthoritiesPK;
import ro.roda.domain.UserGroup;
import ro.roda.domain.UserMessage;
import ro.roda.domain.Users;
import ro.roda.domainjson.AdminJson;
import ro.roda.domainjson.UserActivities;
import ro.roda.domainjson.UserGroupInfo;
import ro.roda.domainjson.UserGroupList;
import ro.roda.domainjson.UserList;
import ro.roda.domainjson.UserMessages;
import ro.roda.domainjson.UsersByGroup;

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

	public AdminJson userSave(Integer id, String username, String password, String passwordCheck, String email,
			Boolean enabled) {
		if (username == null) {
			return new AdminJson(false, "The User must have a name!");
		}
		if (password == null || !password.equals(passwordCheck)) {
			return new AdminJson(false, "The User password is not correct!");
		}
		try {
			Users u = Users.checkUsers(id, username, password, enabled);
		} catch (Exception e) {
			return new AdminJson(false, "Exception saving User: " + e.getMessage());
		}
		return new AdminJson(true, "User created/saved");
	}

	public AdminJson groupSave(Integer id, String name, String description, Boolean enabled) {
		if (name == null) {
			return new AdminJson(false, "The User Group must have a name!");
		}
		try {
			UserGroup ug = UserGroup.checkUserGroup(id, name, description, enabled);
		} catch (Exception e) {
			return new AdminJson(false, "Exception saving User Group: " + e.getMessage());
		}
		return new AdminJson(true, "User Group created/saved");
	}

	public AdminJson userAddToGroup(Integer userId, Integer groupId) {
		Users u = Users.findUsers(userId);
		UserGroup ug = UserGroup.findUserGroup(groupId);
		if (u == null) {
			return new AdminJson(false, "User does not exist");
		}
		if (ug == null) {
			return new AdminJson(false, "User Group does not exist");
		}

		Authorities a = Authorities.findAuthorities(new AuthoritiesPK(u.getUsername(), ug.getGroupname()));
		if (a == null) {
			// add user to group
			a = new Authorities();
			a.setUsername(u);
			a.setAuthority(ug);
			Authorities.entityManager().persist(a);
		}

		return new AdminJson(true, "User added to Group");
	}

	public AdminJson userRemoveFromGroup(Integer userId, Integer groupId) {
		Users u = Users.findUsers(userId);
		UserGroup ug = UserGroup.findUserGroup(groupId);
		if (u == null) {
			return new AdminJson(false, "User does not exist");
		}
		if (ug == null) {
			return new AdminJson(false, "User Group does not exist");
		}

		Authorities a = Authorities.findAuthorities(new AuthoritiesPK(u.getUsername(), ug.getGroupname()));
		if (a != null) {
			Authorities.entityManager().remove(a);
		}

		return new AdminJson(true, "User removed from Group");
	}

	public AdminJson userEnable(Integer userId) {
		Users u = Users.findUsers(userId);
		if (u == null) {
			return new AdminJson(false, "User does not exist");
		}
		u.setEnabled(true);
		Users.entityManager().merge(u);
		return new AdminJson(true, "User enabled");
	}

	public AdminJson userDisable(Integer userId) {
		Users u = Users.findUsers(userId);
		if (u == null) {
			return new AdminJson(false, "User does not exist");
		}
		u.setEnabled(false);
		Users.entityManager().merge(u);
		return new AdminJson(true, "User disabled");
	}

	public AdminJson userDrop(Integer userId) {
		Users u = Users.findUsers(userId);
		if (u == null) {
			return new AdminJson(false, "User does not exist");
		}
		Users.entityManager().remove(u);
		return new AdminJson(true, "User deleted");
	}

	public AdminJson userChangePassword(Integer userId, String password, String controlPassword) {
		if (password == null || password.equals(controlPassword)) {
			return new AdminJson(false, "Passwords do not match");
		}
		Users u = Users.findUsers(userId);
		if (u == null) {
			return new AdminJson(false, "User does not exist");
		}
		u.setPassword(password);
		Users.entityManager().merge(u);
		return new AdminJson(true, "User - changed password");
	}

	public AdminJson userMessage(Integer userId, String subject, String message) {
		Users u = Users.findUsers(userId);
		if (u == null) {
			return new AdminJson(false, "User does not exist");
		}

		UserMessage um = new UserMessage();

		um.setSubject(subject);
		um.setMessage(message);
		um.setRead(false);
		um.setToUserId(u);
		// TODO is it ok to use here as sender the user with ID=1 (admin) ?
		um.setFromUserId(Users.findUsers(new Integer(1)));

		Authorities.entityManager().persist(um);

		return new AdminJson(true, "User message sent (from Admin)");
	}

	public AdminJson groupMessage(Integer groupId, String subject, String message) {
		UserGroup ug = UserGroup.findUserGroup(groupId);
		if (ug == null) {
			return new AdminJson(false, "User Group does not exist");
		}

		for (Authorities a : Authorities.findAllAuthoritieses()) {
			if (ug.equals(a.getAuthority())) {
				userMessage(a.getUsername().getId(), subject, message);
			}
		}

		return new AdminJson(true, "Message sent to all users in group (from Admin");
	}

}
