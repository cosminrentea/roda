package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.UserSettingGroup;

public interface UserSettingGroupService {

	public abstract long countAllUserSettingGroups();

	public abstract void deleteUserSettingGroup(UserSettingGroup userSettingGroup);

	public abstract UserSettingGroup findUserSettingGroup(Integer id);

	public abstract List<UserSettingGroup> findAllUserSettingGroups();

	public abstract List<UserSettingGroup> findUserSettingGroupEntries(int firstResult, int maxResults);

	public abstract void saveUserSettingGroup(UserSettingGroup userSettingGroup);

	public abstract UserSettingGroup updateUserSettingGroup(UserSettingGroup userSettingGroup);

}
