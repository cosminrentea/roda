package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.OrgPhone;

@RepositoryRestResource
public interface OrgPhoneRepository extends PagingAndSortingRepository<OrgPhone, Integer> {

}