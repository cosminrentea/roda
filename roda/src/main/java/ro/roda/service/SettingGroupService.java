package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.SettingGroup;


public interface SettingGroupService {

	public abstract long countAllSettingGroups();


	public abstract void deleteSettingGroup(SettingGroup settingGroup);


	public abstract SettingGroup findSettingGroup(Integer id);


	public abstract List<SettingGroup> findAllSettingGroups();


	public abstract List<SettingGroup> findSettingGroupEntries(int firstResult, int maxResults);


	public abstract void saveSettingGroup(SettingGroup settingGroup);


	public abstract SettingGroup updateSettingGroup(SettingGroup settingGroup);

}
