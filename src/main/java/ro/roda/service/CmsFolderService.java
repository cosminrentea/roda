package ro.roda.service;

import java.util.List;

import ro.roda.domain.CmsFolder;

public interface CmsFolderService {

	public abstract long countAllCmsFolders();

	public abstract void deleteCmsFolder(CmsFolder cmsFolder);

	public abstract CmsFolder findCmsFolder(Integer id);

	public abstract List<CmsFolder> findAllCmsFolders();

	public abstract List<CmsFolder> findCmsFolderEntries(int firstResult, int maxResults);

	public abstract void saveCmsFolder(CmsFolder cmsFolder);

	public abstract CmsFolder updateCmsFolder(CmsFolder cmsFolder);

}
