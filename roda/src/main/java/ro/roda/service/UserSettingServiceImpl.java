package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.UserSetting;


@Service
@Transactional
public class UserSettingServiceImpl implements UserSettingService {

	public long countAllUserSettings() {
        return UserSetting.countUserSettings();
    }

	public void deleteUserSetting(UserSetting userSetting) {
        userSetting.remove();
    }

	public UserSetting findUserSetting(Integer id) {
        return UserSetting.findUserSetting(id);
    }

	public List<UserSetting> findAllUserSettings() {
        return UserSetting.findAllUserSettings();
    }

	public List<UserSetting> findUserSettingEntries(int firstResult, int maxResults) {
        return UserSetting.findUserSettingEntries(firstResult, maxResults);
    }

	public void saveUserSetting(UserSetting userSetting) {
        userSetting.persist();
    }

	public UserSetting updateUserSetting(UserSetting userSetting) {
        return userSetting.merge();
    }
}
