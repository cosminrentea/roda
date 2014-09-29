package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.AclClass;

@RepositoryRestResource
public interface AclClassRepository extends PagingAndSortingRepository<AclClass, Long> {

}