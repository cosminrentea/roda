package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Source;

@RepositoryRestResource
public interface SourceRepository extends PagingAndSortingRepository<Source, Integer> {

}