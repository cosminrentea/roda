package ro.roda.filestore;

import java.io.File;
import java.util.List;
import ro.roda.domain.CmsFile;

import org.springframework.web.multipart.MultipartFile;

public interface CmsFileStoreService {
	
	public abstract long countAllCmsFiles();

	public abstract void deleteCmsFile(CmsFile cmsFile);

	public abstract CmsFile findCmsFile(Integer id);

	public abstract List<CmsFile> findAllCmsFiles();

	public abstract List<CmsFile> findCmsFileEntries(int firstResult, int maxResults);

	public abstract void saveCmsFile(CmsFile cmsFile);

	public abstract void saveCmsFile(CmsFile cmsFile, MultipartFile mFile, String folder);
	
	public File loadFile(String fileFullPath);
	
	public void removeCmsFile(CmsFile cmsFile);
	
	public abstract CmsFile updateCmsFile(CmsFile cmsFile);

}

