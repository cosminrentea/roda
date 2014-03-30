package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.CmsFile;

@Service
@Transactional
public class CmsFileServiceImpl implements CmsFileService {

	public long countAllCmsFiles() {
		return CmsFile.countCmsFiles();
	}

	public void deleteCmsFile(CmsFile cmsFile) {
		cmsFile.remove();
	}

	public CmsFile findCmsFile(Integer id) {
		return CmsFile.findCmsFile(id);
	}

	public CmsFile findCmsFile(String alias) {
		return CmsFile.findCmsFile(alias);
	}

	public List<CmsFile> findAllCmsFiles() {
		return CmsFile.findAllCmsFiles();
	}

	public List<CmsFile> findCmsFileEntries(int firstResult, int maxResults) {
		return CmsFile.findCmsFileEntries(firstResult, maxResults);
	}

	public void saveCmsFile(CmsFile cmsFile) {
		cmsFile.persist();
	}

	public CmsFile updateCmsFile(CmsFile cmsFile) {
		return cmsFile.merge();
	}
}
