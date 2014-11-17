package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Concept;

@RepositoryRestResource
public interface ConceptRepository extends PagingAndSortingRepository<Concept, Integer> {

}