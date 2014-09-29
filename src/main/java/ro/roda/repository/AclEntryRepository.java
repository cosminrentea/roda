package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.AclEntry;

@RepositoryRestResource
public interface AclEntryRepository extends PagingAndSortingRepository<AclEntry, Long> {

}