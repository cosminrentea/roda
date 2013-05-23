package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.PersonRole;

@Service
@Transactional
public class PersonRoleServiceImpl implements PersonRoleService {

	public long countAllPersonRoles() {
		return PersonRole.countPersonRoles();
	}

	public void deletePersonRole(PersonRole personRole) {
		personRole.remove();
	}

	public PersonRole findPersonRole(Integer id) {
		return PersonRole.findPersonRole(id);
	}

	public List<PersonRole> findAllPersonRoles() {
		return PersonRole.findAllPersonRoles();
	}

	public List<PersonRole> findPersonRoleEntries(int firstResult, int maxResults) {
		return PersonRole.findPersonRoleEntries(firstResult, maxResults);
	}

	public void savePersonRole(PersonRole personRole) {
		personRole.persist();
	}

	public PersonRole updatePersonRole(PersonRole personRole) {
		return personRole.merge();
	}
}
