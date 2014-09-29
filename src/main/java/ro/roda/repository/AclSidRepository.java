package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.AclSid;

@RepositoryRestResource
public interface AclSidRepository extends PagingAndSortingRepository<AclSid, Long> {

}