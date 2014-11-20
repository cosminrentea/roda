package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.GeoVersion;

@RepositoryRestResource
public interface GeoVersionRepository extends PagingAndSortingRepository<GeoVersion, Integer> {

}