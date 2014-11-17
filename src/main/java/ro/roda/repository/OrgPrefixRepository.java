package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.OrgPrefix;

@RepositoryRestResource
public interface OrgPrefixRepository extends PagingAndSortingRepository<OrgPrefix, Integer> {

}