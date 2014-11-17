package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.CmsPageType;

@RepositoryRestResource
public interface CmsPageTypeRepository extends PagingAndSortingRepository<CmsPageType, Integer> {

}