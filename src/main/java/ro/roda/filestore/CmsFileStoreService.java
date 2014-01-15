package ro.roda.filestore;

import java.io.File;
import java.util.List;
import ro.roda.domain.CmsFile;
import ro.roda.domain.CmsFolder;
import ro.roda.transformer.AdminJson;

import org.springframework.web.multipart.MultipartFile;

public interface CmsFileStoreService {
		
	public abstract void folderSave(CmsFolder cmsFolder);

	public abstract void fileSave(MultipartFile mFile, CmsFolder cmsFolder);
	
//	public abstract File fileLoad(String fileFullPath);

	public abstract File fileLoad(CmsFile file);
	
	public abstract void folderEmpty(CmsFolder cmsFolder);

	public abstract void folderDrop(CmsFolder cmsFolder);

	public abstract void fileDrop(CmsFile cmsFile);

	public abstract void fileMove(CmsFolder cmsFolder, CmsFile cmsFile);

	public abstract void folderMove(CmsFolder cmsFolderParent, CmsFolder cmsFolder);
	
}

