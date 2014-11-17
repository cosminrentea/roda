package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.TranslatedQuestion;

@RepositoryRestResource
public interface TranslatedQuestionRepository extends PagingAndSortingRepository<TranslatedQuestion, Integer> {

}