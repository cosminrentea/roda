package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.SettingGroup;

@Service
@Transactional
public class SettingGroupServiceImpl implements SettingGroupService {

	public long countAllSettingGroups() {
		return SettingGroup.countSettingGroups();
	}

	public void deleteSettingGroup(SettingGroup settingGroup) {
		settingGroup.remove();
	}

	public SettingGroup findSettingGroup(Integer id) {
		return SettingGroup.findSettingGroup(id);
	}

	public List<SettingGroup> findAllSettingGroups() {
		return SettingGroup.findAllSettingGroups();
	}

	public List<SettingGroup> findSettingGroupEntries(int firstResult, int maxResults) {
		return SettingGroup.findSettingGroupEntries(firstResult, maxResults);
	}

	public void saveSettingGroup(SettingGroup settingGroup) {
		settingGroup.persist();
	}

	public SettingGroup updateSettingGroup(SettingGroup settingGroup) {
		return settingGroup.merge();
	}
}
