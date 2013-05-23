package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.CmsSnippetGroup;

@Service
@Transactional
public class CmsSnippetGroupServiceImpl implements CmsSnippetGroupService {

	public long countAllCmsSnippetGroups() {
		return CmsSnippetGroup.countCmsSnippetGroups();
	}

	public void deleteCmsSnippetGroup(CmsSnippetGroup cmsSnippetGroup) {
		cmsSnippetGroup.remove();
	}

	public CmsSnippetGroup findCmsSnippetGroup(Integer id) {
		return CmsSnippetGroup.findCmsSnippetGroup(id);
	}

	public List<CmsSnippetGroup> findAllCmsSnippetGroups() {
		return CmsSnippetGroup.findAllCmsSnippetGroups();
	}

	public List<CmsSnippetGroup> findCmsSnippetGroupEntries(int firstResult, int maxResults) {
		return CmsSnippetGroup.findCmsSnippetGroupEntries(firstResult, maxResults);
	}

	public void saveCmsSnippetGroup(CmsSnippetGroup cmsSnippetGroup) {
		cmsSnippetGroup.persist();
	}

	public CmsSnippetGroup updateCmsSnippetGroup(CmsSnippetGroup cmsSnippetGroup) {
		return cmsSnippetGroup.merge();
	}
}
