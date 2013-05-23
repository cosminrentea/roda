package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.UserSetting;

public interface UserSettingService {

	public abstract long countAllUserSettings();

	public abstract void deleteUserSetting(UserSetting userSetting);

	public abstract UserSetting findUserSetting(Integer id);

	public abstract List<UserSetting> findAllUserSettings();

	public abstract List<UserSetting> findUserSettingEntries(int firstResult, int maxResults);

	public abstract void saveUserSetting(UserSetting userSetting);

	public abstract UserSetting updateUserSetting(UserSetting userSetting);

}
