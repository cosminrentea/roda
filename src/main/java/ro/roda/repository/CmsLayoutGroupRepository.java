package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.CmsLayoutGroup;

@RepositoryRestResource
public interface CmsLayoutGroupRepository extends PagingAndSortingRepository<CmsLayoutGroup, Integer> {

}