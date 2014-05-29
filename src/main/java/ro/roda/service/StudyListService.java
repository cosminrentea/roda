package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.StudyList;

public interface StudyListService {

	public abstract List<StudyList> findAllStudyLists();

	public abstract StudyList findStudyList(Integer id);

}
