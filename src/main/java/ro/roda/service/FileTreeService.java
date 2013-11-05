package ro.roda.service;

import java.util.List;

import ro.roda.transformer.FileTree;

public interface FileTreeService {

	public abstract List<FileTree> findAllFileTrees();

	public abstract FileTree findFileTree(Integer id);

}
