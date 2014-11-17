package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.CmsSnippet;

@RepositoryRestResource
public interface CmsSnippetRepository extends PagingAndSortingRepository<CmsSnippet, Integer> {

}