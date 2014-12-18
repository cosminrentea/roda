package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.DdiEditorCountryList;

@Service
@Transactional
public class DdiEditorCountryListServiceImpl implements DdiEditorCountryListService {

	public List<DdiEditorCountryList> findAllCountries() {
		return DdiEditorCountryList.findAllCountries();
	}

	public DdiEditorCountryList findCountry(Integer id) {
		return DdiEditorCountryList.findCountry(id);
	}
}
