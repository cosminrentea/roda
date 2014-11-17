package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.CmsPageLang;

@RepositoryRestResource
public interface CmsPageLangRepository extends PagingAndSortingRepository<CmsPageLang, Integer> {

}