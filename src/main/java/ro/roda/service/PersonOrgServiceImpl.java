package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.PersonOrg;
import ro.roda.domain.PersonOrgPK;

@Service
@Transactional
public class PersonOrgServiceImpl implements PersonOrgService {

	public long countAllPersonOrgs() {
		return PersonOrg.countPersonOrgs();
	}

	public void deletePersonOrg(PersonOrg personOrg) {
		personOrg.remove();
	}

	public PersonOrg findPersonOrg(PersonOrgPK id) {
		return PersonOrg.findPersonOrg(id);
	}

	public List<PersonOrg> findAllPersonOrgs() {
		return PersonOrg.findAllPersonOrgs();
	}

	public List<PersonOrg> findPersonOrgEntries(int firstResult, int maxResults) {
		return PersonOrg.findPersonOrgEntries(firstResult, maxResults);
	}

	public void savePersonOrg(PersonOrg personOrg) {
		personOrg.persist();
	}

	public PersonOrg updatePersonOrg(PersonOrg personOrg) {
		return personOrg.merge();
	}
}
