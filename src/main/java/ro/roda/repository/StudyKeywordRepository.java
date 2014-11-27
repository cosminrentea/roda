package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.StudyKeyword;
import ro.roda.domain.StudyKeywordPK;

@RepositoryRestResource
public interface StudyKeywordRepository extends PagingAndSortingRepository<StudyKeyword, StudyKeywordPK> {

}