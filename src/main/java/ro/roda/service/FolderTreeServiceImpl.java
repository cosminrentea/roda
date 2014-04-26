package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.FolderTree;

@Service
@Transactional
public class FolderTreeServiceImpl implements FolderTreeService {

	public List<FolderTree> findAllFolderTrees() {
		return FolderTree.findAllFolderTrees();
	}

	public FolderTree findFolderTree(Integer id) {
		return FolderTree.findFolderTree(id);
	}
}
