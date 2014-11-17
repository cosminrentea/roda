package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Address;

@RepositoryRestResource
public interface AddressRepository extends PagingAndSortingRepository<Address, Integer> {

}