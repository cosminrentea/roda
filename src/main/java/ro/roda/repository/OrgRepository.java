package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Org;

@RepositoryRestResource
public interface OrgRepository extends PagingAndSortingRepository<Org, Integer> {

}