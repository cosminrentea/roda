package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.CmsMenu;

@RepositoryRestResource
public interface CmsMenuRepository extends PagingAndSortingRepository<CmsMenu, Integer> {

}