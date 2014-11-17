package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Vargroup;

@RepositoryRestResource
public interface VargroupRepository extends PagingAndSortingRepository<Vargroup, Integer> {

}