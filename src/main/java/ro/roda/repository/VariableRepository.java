package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Variable;

@RepositoryRestResource
public interface VariableRepository extends PagingAndSortingRepository<Variable, Integer> {

}