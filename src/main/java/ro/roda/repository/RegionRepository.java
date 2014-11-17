package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Region;

@RepositoryRestResource
public interface RegionRepository extends PagingAndSortingRepository<Region, Integer> {

}