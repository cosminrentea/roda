package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Skip;

@RepositoryRestResource
public interface SkipRepository extends PagingAndSortingRepository<Skip, Integer> {

}