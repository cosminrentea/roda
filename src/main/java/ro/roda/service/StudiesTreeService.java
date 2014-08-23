package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.StudiesTree;

public interface StudiesTreeService {

	public abstract List<StudiesTree> findAllStudiesTrees();

	public abstract StudiesTree findStudiesTree(Integer id);

}
