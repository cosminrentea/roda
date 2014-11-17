package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Country;

@RepositoryRestResource
public interface CountryRepository extends PagingAndSortingRepository<Country, Integer> {

}