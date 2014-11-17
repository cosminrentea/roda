package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.PersonPhone;

@RepositoryRestResource
public interface PersonPhoneRepository extends PagingAndSortingRepository<PersonPhone, Integer> {

}