package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.InstancePerson;

@RepositoryRestResource
public interface InstancePersonRepository extends PagingAndSortingRepository<InstancePerson, Integer> {

}