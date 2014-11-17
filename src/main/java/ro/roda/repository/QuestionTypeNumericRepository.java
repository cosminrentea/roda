package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.QuestionTypeNumeric;

@RepositoryRestResource
public interface QuestionTypeNumericRepository extends PagingAndSortingRepository<QuestionTypeNumeric, Integer> {

}