package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.PersonInternet;
import ro.roda.domain.PersonInternetPK;


@Service
@Transactional
public class PersonInternetServiceImpl implements PersonInternetService {

	public long countAllPersonInternets() {
        return PersonInternet.countPersonInternets();
    }

	public void deletePersonInternet(PersonInternet personInternet) {
        personInternet.remove();
    }

	public PersonInternet findPersonInternet(PersonInternetPK id) {
        return PersonInternet.findPersonInternet(id);
    }

	public List<PersonInternet> findAllPersonInternets() {
        return PersonInternet.findAllPersonInternets();
    }

	public List<PersonInternet> findPersonInternetEntries(int firstResult, int maxResults) {
        return PersonInternet.findPersonInternetEntries(firstResult, maxResults);
    }

	public void savePersonInternet(PersonInternet personInternet) {
        personInternet.persist();
    }

	public PersonInternet updatePersonInternet(PersonInternet personInternet) {
        return personInternet.merge();
    }
}
