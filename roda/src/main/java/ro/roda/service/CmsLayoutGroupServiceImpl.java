package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.CmsLayoutGroup;

@Service
@Transactional
public class CmsLayoutGroupServiceImpl implements CmsLayoutGroupService {

	public long countAllCmsLayoutGroups() {
		return CmsLayoutGroup.countCmsLayoutGroups();
	}

	public void deleteCmsLayoutGroup(CmsLayoutGroup cmsLayoutGroup) {
		cmsLayoutGroup.remove();
	}

	public CmsLayoutGroup findCmsLayoutGroup(Integer id) {
		return CmsLayoutGroup.findCmsLayoutGroup(id);
	}

	public List<CmsLayoutGroup> findAllCmsLayoutGroups() {
		return CmsLayoutGroup.findAllCmsLayoutGroups();
	}

	public List<CmsLayoutGroup> findCmsLayoutGroupEntries(int firstResult, int maxResults) {
		return CmsLayoutGroup.findCmsLayoutGroupEntries(firstResult, maxResults);
	}

	public void saveCmsLayoutGroup(CmsLayoutGroup cmsLayoutGroup) {
		cmsLayoutGroup.persist();
	}

	public CmsLayoutGroup updateCmsLayoutGroup(CmsLayoutGroup cmsLayoutGroup) {
		return cmsLayoutGroup.merge();
	}
}
