package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.StudyPersonAssoc;

@RepositoryRestResource
public interface StudyPersonAssocRepository extends PagingAndSortingRepository<StudyPersonAssoc, Integer> {

}