package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.OrgSufix;

@RepositoryRestResource
public interface OrgSufixRepository extends PagingAndSortingRepository<OrgSufix, Integer> {

}