package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.PersonRole;


public interface PersonRoleService {

	public abstract long countAllPersonRoles();


	public abstract void deletePersonRole(PersonRole personRole);


	public abstract PersonRole findPersonRole(Integer id);


	public abstract List<PersonRole> findAllPersonRoles();


	public abstract List<PersonRole> findPersonRoleEntries(int firstResult, int maxResults);


	public abstract void savePersonRole(PersonRole personRole);


	public abstract PersonRole updatePersonRole(PersonRole personRole);

}
