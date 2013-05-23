package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.PersonInternet;
import ro.roda.domain.PersonInternetPK;


public interface PersonInternetService {

	public abstract long countAllPersonInternets();


	public abstract void deletePersonInternet(PersonInternet personInternet);


	public abstract PersonInternet findPersonInternet(PersonInternetPK id);


	public abstract List<PersonInternet> findAllPersonInternets();


	public abstract List<PersonInternet> findPersonInternetEntries(int firstResult, int maxResults);


	public abstract void savePersonInternet(PersonInternet personInternet);


	public abstract PersonInternet updatePersonInternet(PersonInternet personInternet);

}
