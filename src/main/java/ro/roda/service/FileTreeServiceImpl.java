package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.transformer.FileTree;

@Service
@Transactional
public class FileTreeServiceImpl implements FileTreeService {

	public List<FileTree> findAllFileTrees() {
		return FileTree.findAllFileTrees();
	}

	public FileTree findFileTree(Integer id) {
		return FileTree.findFolderTree(id);
	}
}
