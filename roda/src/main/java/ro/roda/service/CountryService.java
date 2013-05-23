package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.Country;

public interface CountryService {

	public abstract long countAllCountrys();

	public abstract void deleteCountry(Country country);

	public abstract Country findCountry(Integer id);

	public abstract List<Country> findAllCountrys();

	public abstract List<Country> findCountryEntries(int firstResult, int maxResults);

	public abstract void saveCountry(Country country);

	public abstract Country updateCountry(Country country);

}
