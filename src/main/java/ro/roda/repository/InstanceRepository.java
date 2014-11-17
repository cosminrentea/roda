package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Instance;

@RepositoryRestResource
public interface InstanceRepository extends PagingAndSortingRepository<Instance, Integer> {

}