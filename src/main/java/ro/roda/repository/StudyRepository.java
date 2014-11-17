package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Study;

@RepositoryRestResource
public interface StudyRepository extends PagingAndSortingRepository<Study, Integer> {

}