package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.SamplingProcedure;

@RepositoryRestResource
public interface SamplingProcedureRepository extends PagingAndSortingRepository<SamplingProcedure, Integer> {

}