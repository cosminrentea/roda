package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.MissingValue;

@RepositoryRestResource
public interface MissingValueRepository extends PagingAndSortingRepository<MissingValue, Integer> {

}