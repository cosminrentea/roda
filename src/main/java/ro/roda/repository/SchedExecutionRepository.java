package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.SchedExecution;

@RepositoryRestResource
public interface SchedExecutionRepository extends PagingAndSortingRepository<SchedExecution, Integer> {

}