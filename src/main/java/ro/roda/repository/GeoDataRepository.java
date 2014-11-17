package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.GeoData;

@RepositoryRestResource
public interface GeoDataRepository extends PagingAndSortingRepository<GeoData, Integer> {

}