package ro.roda.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.Authorities;
import ro.roda.domain.AuthoritiesPK;
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

	public AdminJson userCreate(String authorityName, String username, String password, String passwordCheck,
			Boolean enabled, String email, String firstname, String middlename, String lastname, String title,
			String sex, String salutation, Date birthdate, String address1, String address2) {
		if (username == null || username.length() == 0) {
			return new AdminJson(false, "The User must have a name!");
		}
		if (password == null || !password.equals(passwordCheck)) {
			return new AdminJson(false, "The User password is not correct!");
		}
		try {
			// create new User with username + password
			Users u = Users.create(username, password, enabled, email, firstname, middlename, lastname, title, sex,
					salutation, birthdate, address1, address2);
			// set one initial Authority for the new User
			Authorities a = Authorities.findOrCreate(u, authorityName);
			return new AdminJson(true, "User was created", u.getId());
		} catch (Exception e) {
			return new AdminJson(false, "Exception when creating User: " + e.getMessage());
		}
	}

	public AdminJson userSave(Integer userId, String username, String email, String firstname, String middlename,
			String lastname, String title, String sex, String salutation, Date birthdate, String address1,
			String address2) {
		if ((userId == null) && (username == null || username.length() == 0)) {
			return new AdminJson(false, "User was NOT saved: The User must have a name or ID!");
		}
		try {
			Users u = Users.save(userId, username, email, firstname, middlename, lastname, title, sex, salutation,
					birthdate, address1, address2);
			if (u == null) {
				return new AdminJson(false, "User was NOT saved");
			}
			return new AdminJson(true, "User was saved", u.getId());
		} catch (Exception e) {
			return new AdminJson(false, "Exception when saving User: " + e.getMessage());
		}
	}

	public AdminJson userDrop(Integer userId) {
		try {
			Users u = Users.findUsers(userId);
			if (u == null) {
				return new AdminJson(false, "User was NOT dropped: User does not exist");
			}
			Users.entityManager().remove(u);
			return new AdminJson(true, "User was permanently dropped");
		} catch (Exception e) {
			return new AdminJson(false, "Exception when dropping User: " + e.getMessage());
		}
	}

	@Override
	public AdminJson userAddAuthority(Integer userId, String authorityName) {
		Users u = Users.findUsers(userId);
		if (u == null) {
			return new AdminJson(false, "Authority was NOT added to User: User does not exist");
		}
		Authorities a = Authorities.findOrCreate(u, authorityName);
		return new AdminJson(true, "Authority was added to User");
	}

	public AdminJson userRemoveAuthority(Integer userId, String authorityName) {
		Users u = Users.findUsers(userId);
		if (u == null) {
			return new AdminJson(false, "Authority was NOT removed from User: User does not exist");
		}
		Authorities a = Authorities.findAuthorities(new AuthoritiesPK(u.getUsername(), authorityName));
		if (a == null) {
			return new AdminJson(false,
					"Authority was NOT removed from User: the User did not already have the Authority");
		}
		try {
			Authorities.entityManager().remove(a);
			return new AdminJson(true, "Authority was removed from User");
		} catch (Exception e) {
			return new AdminJson(false, "Exception when removing Authority from User: " + e.getMessage());
		}
	}

	public AdminJson userEnable(Integer userId) {
		try {
			Users u = Users.findUsers(userId);
			if (u == null) {
				return new AdminJson(false, "User was NOT enabled: User does not exist");
			}
			u.setEnabled(true);
			Users.entityManager().merge(u);
			return new AdminJson(true, "User was enabled");
		} catch (Exception e) {
			return new AdminJson(false, "Exception when enabling User: " + e.getMessage());
		}
	}

	public AdminJson userDisable(Integer userId) {
		try {
			Users u = Users.findUsers(userId);
			if (u == null) {
				return new AdminJson(false, "User was NOT disabled: User does not exist");
			}
			u.setEnabled(false);
			Users.entityManager().merge(u);
			return new AdminJson(true, "User was disabled");
		} catch (Exception e) {
			return new AdminJson(false, "Exception when disabling User: " + e.getMessage());
		}
	}

	public AdminJson userChangePassword(Integer userId, String password, String passwordCheck) {
		try {
			if (password == null || !password.equals(passwordCheck)) {
				return new AdminJson(false, "Password was NOT changed for User: Passwords do not match");
			}
			Users u = Users.findUsers(userId);
			if (u == null) {
				return new AdminJson(false, "Password was NOT changed for User: User does not exist");
			}
			u.setPassword(password);
			Users.entityManager().merge(u);
			return new AdminJson(true, "Password was changed for User");
		} catch (Exception e) {
			return new AdminJson(false, "Exception when changing User password: " + e.getMessage());
		}
	}

	public AdminJson userMessage(Integer userId, String subject, String message) {
		try {
			Users u = Users.findUsers(userId);
			if (u == null) {
				return new AdminJson(false, "Message was NOT sent: User does not exist");
			}

			UserMessage um = new UserMessage();

			um.setSubject(subject);
			um.setMessage(message);
			um.setRead(false);
			um.setToUserId(u);
			// TODO is it ok to use here as sender the user with ID=1 (admin) ?
			um.setFromUserId(Users.findUsers(new Integer(1)));

			Authorities.entityManager().persist(um);

			return new AdminJson(true, "User message sent (from the Admin user)");
		} catch (Exception e) {
			return new AdminJson(false, "Exception when sending message: " + e.getMessage());
		}
	}

	public AdminJson groupMessage(String authorityName, String subject, String message) {
		if (authorityName != null) {
			for (Authorities a : Authorities.findAllAuthoritieses()) {
				if (authorityName.equals(a.getId().getAuthority())) {
					userMessage(a.getUsername().getId(), subject, message);
				}
			}
		}
		return new AdminJson(true, "Message was sent to all Users having this Authority (sent from the admin user)");
	}

}
