package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.DdiEditorPersonList;

public interface DdiEditorPersonListService {

	public abstract List<DdiEditorPersonList> findAllPersons();

	public abstract DdiEditorPersonList findPerson(Integer id);

}
