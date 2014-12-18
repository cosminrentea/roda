package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.DdiEditorCountryList;

public interface DdiEditorCountryListService {

	public abstract List<DdiEditorCountryList> findAllCountries();

	public abstract DdiEditorCountryList findCountry(Integer id);

}
