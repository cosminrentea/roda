package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.City;

public interface CityService {

	public abstract long countAllCitys();

	public abstract void deleteCity(City city);

	public abstract City findCity(Integer id);

	public abstract List<City> findAllCitys();

	public abstract List<City> findCityEntries(int firstResult, int maxResults);

	public abstract void saveCity(City city);

	public abstract City updateCity(City city);

}
