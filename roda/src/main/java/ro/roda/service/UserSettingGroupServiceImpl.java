package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.UserSettingGroup;

@Service
@Transactional
public class UserSettingGroupServiceImpl implements UserSettingGroupService {

	public long countAllUserSettingGroups() {
		return UserSettingGroup.countUserSettingGroups();
	}

	public void deleteUserSettingGroup(UserSettingGroup userSettingGroup) {
		userSettingGroup.remove();
	}

	public UserSettingGroup findUserSettingGroup(Integer id) {
		return UserSettingGroup.findUserSettingGroup(id);
	}

	public List<UserSettingGroup> findAllUserSettingGroups() {
		return UserSettingGroup.findAllUserSettingGroups();
	}

	public List<UserSettingGroup> findUserSettingGroupEntries(int firstResult, int maxResults) {
		return UserSettingGroup.findUserSettingGroupEntries(firstResult, maxResults);
	}

	public void saveUserSettingGroup(UserSettingGroup userSettingGroup) {
		userSettingGroup.persist();
	}

	public UserSettingGroup updateUserSettingGroup(UserSettingGroup userSettingGroup) {
		return userSettingGroup.merge();
	}
}
