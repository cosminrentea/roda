package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Geography;

@RepositoryRestResource
public interface GeographyRepository extends PagingAndSortingRepository<Geography, Integer> {

}