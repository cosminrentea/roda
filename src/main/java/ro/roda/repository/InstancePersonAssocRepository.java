package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.InstancePersonAssoc;

@RepositoryRestResource
public interface InstancePersonAssocRepository extends PagingAndSortingRepository<InstancePersonAssoc, Integer> {

}