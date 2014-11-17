package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.StudySaved;

@RepositoryRestResource
public interface StudySavedRepository extends PagingAndSortingRepository<StudySaved, Integer> {

}