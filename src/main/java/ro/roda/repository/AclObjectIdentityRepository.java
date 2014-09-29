package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.AclObjectIdentity;

@RepositoryRestResource
public interface AclObjectIdentityRepository extends PagingAndSortingRepository<AclObjectIdentity, Long> {

}