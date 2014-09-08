package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.FileList;

public interface FileListService {

	public abstract List<FileList> findAllFileLists();

	public abstract FileList findFileList(Integer id);

	public abstract List<FileList> findAllFileListsJson();

}
