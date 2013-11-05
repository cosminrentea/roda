package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.CmsFolder;

@Service
@Transactional
public class CmsFolderServiceImpl implements CmsFolderService {

	public long countAllCmsFolders() {
		return CmsFolder.countCmsFolders();
	}

	public void deleteCmsFolder(CmsFolder cmsFolder) {
		cmsFolder.remove();
	}

	public CmsFolder findCmsFolder(Integer id) {
		return CmsFolder.findCmsFolder(id);
	}

	public List<CmsFolder> findAllCmsFolders() {
		return CmsFolder.findAllCmsFolders();
	}

	public List<CmsFolder> findCmsFolderEntries(int firstResult, int maxResults) {
		return CmsFolder.findCmsFolderEntries(firstResult, maxResults);
	}

	public void saveCmsFolder(CmsFolder cmsFolder) {
		cmsFolder.persist();
	}

	public CmsFolder updateCmsFolder(CmsFolder cmsFolder) {
		return cmsFolder.merge();
	}
}
