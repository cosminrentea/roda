package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.PersonRole;

@RepositoryRestResource
public interface PersonRoleRepository extends PagingAndSortingRepository<PersonRole, Integer> {

}