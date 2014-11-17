package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.StudyKeyword;

@RepositoryRestResource
public interface StudyKeywordRepository extends PagingAndSortingRepository<StudyKeyword, Integer> {

}