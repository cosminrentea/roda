package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.UserSettingValue;
import ro.roda.domain.UserSettingValuePK;


public interface UserSettingValueService {

	public abstract long countAllUserSettingValues();


	public abstract void deleteUserSettingValue(UserSettingValue userSettingValue);


	public abstract UserSettingValue findUserSettingValue(UserSettingValuePK id);


	public abstract List<UserSettingValue> findAllUserSettingValues();


	public abstract List<UserSettingValue> findUserSettingValueEntries(int firstResult, int maxResults);


	public abstract void saveUserSettingValue(UserSettingValue userSettingValue);


	public abstract UserSettingValue updateUserSettingValue(UserSettingValue userSettingValue);

}
