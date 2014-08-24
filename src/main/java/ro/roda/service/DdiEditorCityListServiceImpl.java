package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.DdiEditorCityList;

@Service
@Transactional
public class DdiEditorCityListServiceImpl implements DdiEditorCityListService {

	public List<DdiEditorCityList> findAllCities() {
		return DdiEditorCityList.findAllCities();
	}

	public DdiEditorCityList findCity(Integer id) {
		return DdiEditorCityList.findCity(id);
	}
}
