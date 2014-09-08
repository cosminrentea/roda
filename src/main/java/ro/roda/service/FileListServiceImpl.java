package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.FileList;

@Service
@Transactional
public class FileListServiceImpl implements FileListService {

	public List<FileList> findAllFileLists() {
		return FileList.findAllFileLists();
	}

	public List<FileList> findAllFileListsJson() {
		return FileList.findAllFileListsJson();
	}

	public FileList findFileList(Integer id) {
		return FileList.findFileList(id);
	}
}
