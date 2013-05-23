package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.Setting;


public interface SettingService {

	public abstract long countAllSettings();


	public abstract void deleteSetting(Setting setting);


	public abstract Setting findSetting(Integer id);


	public abstract List<Setting> findAllSettings();


	public abstract List<Setting> findSettingEntries(int firstResult, int maxResults);


	public abstract void saveSetting(Setting setting);


	public abstract Setting updateSetting(Setting setting);

}
