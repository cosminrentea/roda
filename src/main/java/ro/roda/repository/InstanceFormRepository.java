package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.InstanceForm;

@RepositoryRestResource
public interface InstanceFormRepository extends PagingAndSortingRepository<InstanceForm, Integer> {

}