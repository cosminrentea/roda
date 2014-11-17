package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.TimeMeth;

@RepositoryRestResource
public interface TimeMethRepository extends PagingAndSortingRepository<TimeMeth, Integer> {

}