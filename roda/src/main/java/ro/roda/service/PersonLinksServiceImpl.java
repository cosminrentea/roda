package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.PersonLinks;


@Service
@Transactional
public class PersonLinksServiceImpl implements PersonLinksService {

	public long countAllPersonLinkses() {
        return PersonLinks.countPersonLinkses();
    }

	public void deletePersonLinks(PersonLinks personLinks) {
        personLinks.remove();
    }

	public PersonLinks findPersonLinks(Integer id) {
        return PersonLinks.findPersonLinks(id);
    }

	public List<PersonLinks> findAllPersonLinkses() {
        return PersonLinks.findAllPersonLinkses();
    }

	public List<PersonLinks> findPersonLinksEntries(int firstResult, int maxResults) {
        return PersonLinks.findPersonLinksEntries(firstResult, maxResults);
    }

	public void savePersonLinks(PersonLinks personLinks) {
        personLinks.persist();
    }

	public PersonLinks updatePersonLinks(PersonLinks personLinks) {
        return personLinks.merge();
    }
}
