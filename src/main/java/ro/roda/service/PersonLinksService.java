package ro.roda.service;

import java.util.List;

import ro.roda.domain.PersonLinks;

public interface PersonLinksService {

	public abstract long countAllPersonLinkses();

	public abstract void deletePersonLinks(PersonLinks personLinks);

	public abstract PersonLinks findPersonLinks(Integer id);

	public abstract List<PersonLinks> findAllPersonLinkses();

	public abstract List<PersonLinks> findPersonLinksEntries(int firstResult, int maxResults);

	public abstract void savePersonLinks(PersonLinks personLinks);

	public abstract PersonLinks updatePersonLinks(PersonLinks personLinks);

}
