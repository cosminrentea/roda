package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.CmsLayoutGroup;


public interface CmsLayoutGroupService {

	public abstract long countAllCmsLayoutGroups();


	public abstract void deleteCmsLayoutGroup(CmsLayoutGroup cmsLayoutGroup);


	public abstract CmsLayoutGroup findCmsLayoutGroup(Integer id);


	public abstract List<CmsLayoutGroup> findAllCmsLayoutGroups();


	public abstract List<CmsLayoutGroup> findCmsLayoutGroupEntries(int firstResult, int maxResults);


	public abstract void saveCmsLayoutGroup(CmsLayoutGroup cmsLayoutGroup);


	public abstract CmsLayoutGroup updateCmsLayoutGroup(CmsLayoutGroup cmsLayoutGroup);

}
