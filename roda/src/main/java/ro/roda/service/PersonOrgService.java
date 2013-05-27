package ro.roda.service;

import java.util.List;

import ro.roda.domain.PersonOrg;
import ro.roda.domain.PersonOrgPK;

public interface PersonOrgService {

	public abstract long countAllPersonOrgs();

	public abstract void deletePersonOrg(PersonOrg personOrg);

	public abstract PersonOrg findPersonOrg(PersonOrgPK id);

	public abstract List<PersonOrg> findAllPersonOrgs();

	public abstract List<PersonOrg> findPersonOrgEntries(int firstResult, int maxResults);

	public abstract void savePersonOrg(PersonOrg personOrg);

	public abstract PersonOrg updatePersonOrg(PersonOrg personOrg);

}
