package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.FolderTree;

public interface FolderTreeService {

	public abstract List<FolderTree> findAllFolderTrees();

	public abstract FolderTree findFolderTree(Integer id);

}
