package ro.roda.service.filestore;

import java.io.InputStream;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import ro.roda.domain.CmsFile;
import ro.roda.domain.CmsFolder;

public interface CmsFileStoreService {

	public abstract void folderSave(CmsFolder cmsFolder);

	public abstract void fileSave(MultipartFile mFile, CmsFolder cmsFolder);

	public abstract Map<String, String> getFileProperties(CmsFile cmsFile);

	public abstract InputStream fileLoad(CmsFile file);

	public abstract void folderEmpty(CmsFolder cmsFolder);

	public abstract void folderDrop(CmsFolder cmsFolder);

	public abstract void fileDrop(CmsFile cmsFile);

	public abstract void fileMove(CmsFolder cmsFolder, CmsFile cmsFile);

	public abstract void folderMove(CmsFolder cmsFolderParent, CmsFolder cmsFolder);

}
