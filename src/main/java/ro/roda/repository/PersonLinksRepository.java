package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.PersonLinks;

@RepositoryRestResource
public interface PersonLinksRepository extends PagingAndSortingRepository<PersonLinks, Integer> {

}