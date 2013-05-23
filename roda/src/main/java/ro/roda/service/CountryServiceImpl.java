package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Country;


@Service
@Transactional
public class CountryServiceImpl implements CountryService {

	public long countAllCountrys() {
        return Country.countCountrys();
    }

	public void deleteCountry(Country country) {
        country.remove();
    }

	public Country findCountry(Integer id) {
        return Country.findCountry(id);
    }

	public List<Country> findAllCountrys() {
        return Country.findAllCountrys();
    }

	public List<Country> findCountryEntries(int firstResult, int maxResults) {
        return Country.findCountryEntries(firstResult, maxResults);
    }

	public void saveCountry(Country country) {
        country.persist();
    }

	public Country updateCountry(Country country) {
        return country.merge();
    }
}
