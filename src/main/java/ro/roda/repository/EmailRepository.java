package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Email;

@RepositoryRestResource
public interface EmailRepository extends PagingAndSortingRepository<Email, Integer> {

}