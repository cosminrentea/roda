package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.City;

@Service
@Transactional
public class CityServiceImpl implements CityService {

	public long countAllCitys() {
		return City.countCitys();
	}

	public void deleteCity(City city) {
		city.remove();
	}

	public City findCity(Integer id) {
		return City.findCity(id);
	}

	public List<City> findAllCitys() {
		return City.findAllCitys();
	}

	public List<City> findCityEntries(int firstResult, int maxResults) {
		return City.findCityEntries(firstResult, maxResults);
	}

	public void saveCity(City city) {
		city.persist();
	}

	public City updateCity(City city) {
		return city.merge();
	}
}
