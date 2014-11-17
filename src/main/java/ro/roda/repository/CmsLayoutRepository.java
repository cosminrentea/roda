package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.CmsLayout;

@RepositoryRestResource
public interface CmsLayoutRepository extends PagingAndSortingRepository<CmsLayout, Integer> {

}