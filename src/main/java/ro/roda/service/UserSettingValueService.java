package ro.roda.service;

import java.util.List;

import ro.roda.domain.UserSetting;
import ro.roda.domain.UserSettingValue;
import ro.roda.domain.UserSettingValuePK;
import ro.roda.domain.Users;

public interface UserSettingValueService {

	public abstract long countAllUserSettingValues();

	public abstract void deleteUserSettingValue(UserSettingValue userSettingValue);

	public abstract UserSettingValue findUserSettingValue(UserSettingValuePK id);

	public abstract List<UserSettingValue> findAllUserSettingValuesByUser(Users user);

	public abstract List<UserSettingValue> findUserSettingValueByUserAndSettingName(String username,
			String userSettingName);

	public abstract void setOrAddUserSettingValue(String username, String userSettingName, String userSettingValue);

	public abstract List<UserSettingValue> findAllUserSettingValues();

	public abstract List<UserSettingValue> findUserSettingValueEntries(int firstResult, int maxResults);

	public abstract void saveUserSettingValue(UserSettingValue userSettingValue);

	public abstract UserSettingValue updateUserSettingValue(UserSettingValue userSettingValue);

}
