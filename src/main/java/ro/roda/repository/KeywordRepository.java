package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Keyword;

@RepositoryRestResource
public interface KeywordRepository extends PagingAndSortingRepository<Keyword, Integer> {

}