package ro.roda.service;

import java.util.List;

import ro.roda.transformer.FolderInfo;

public interface FolderInfoService {

	public abstract List<FolderInfo> findAllFolderInfos();

	public abstract FolderInfo findFolderInfo(Integer id);

}
