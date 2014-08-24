package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.DdiEditorCityList;

public interface DdiEditorCityListService {

	public abstract List<DdiEditorCityList> findAllCities();

	public abstract DdiEditorCityList findCity(Integer id);

}
