package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.StudyDescr;

@RepositoryRestResource
public interface StudyDescrRepository extends PagingAndSortingRepository<StudyDescr, Integer> {

}