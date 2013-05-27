package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.Setting;

@Service
@Transactional
public class SettingServiceImpl implements SettingService {

	public long countAllSettings() {
		return Setting.countSettings();
	}

	public void deleteSetting(Setting setting) {
		setting.remove();
	}

	public Setting findSetting(Integer id) {
		return Setting.findSetting(id);
	}

	public List<Setting> findAllSettings() {
		return Setting.findAllSettings();
	}

	public List<Setting> findSettingEntries(int firstResult, int maxResults) {
		return Setting.findSettingEntries(firstResult, maxResults);
	}

	public void saveSetting(Setting setting) {
		setting.persist();
	}

	public Setting updateSetting(Setting setting) {
		return setting.merge();
	}
}
