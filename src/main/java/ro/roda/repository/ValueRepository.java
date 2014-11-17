package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Value;

@RepositoryRestResource
public interface ValueRepository extends PagingAndSortingRepository<Value, Integer> {

}