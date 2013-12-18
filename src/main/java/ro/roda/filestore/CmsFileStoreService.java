package ro.roda.filestore;

import java.io.File;
import java.util.List;
import ro.roda.domain.CmsFile;
import ro.roda.domain.CmsFolder;

import org.springframework.web.multipart.MultipartFile;

public interface CmsFileStoreService {
		
	public abstract void saveCmsFolder(CmsFolder cmsFolder);

	public abstract void saveCmsFile(MultipartFile mFile, Integer cmsFolderId);
	
	public abstract File loadFile(String fileFullPath);

	public abstract File loadFile(CmsFile file);
	
	public abstract void removeCmsFile(CmsFile cmsFile);
	
}

