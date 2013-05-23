package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.UserSettingValue;
import ro.roda.domain.UserSettingValuePK;


@Service
@Transactional
public class UserSettingValueServiceImpl implements UserSettingValueService {

	public long countAllUserSettingValues() {
        return UserSettingValue.countUserSettingValues();
    }

	public void deleteUserSettingValue(UserSettingValue userSettingValue) {
        userSettingValue.remove();
    }

	public UserSettingValue findUserSettingValue(UserSettingValuePK id) {
        return UserSettingValue.findUserSettingValue(id);
    }

	public List<UserSettingValue> findAllUserSettingValues() {
        return UserSettingValue.findAllUserSettingValues();
    }

	public List<UserSettingValue> findUserSettingValueEntries(int firstResult, int maxResults) {
        return UserSettingValue.findUserSettingValueEntries(firstResult, maxResults);
    }

	public void saveUserSettingValue(UserSettingValue userSettingValue) {
        userSettingValue.persist();
    }

	public UserSettingValue updateUserSettingValue(UserSettingValue userSettingValue) {
        return userSettingValue.merge();
    }
}
