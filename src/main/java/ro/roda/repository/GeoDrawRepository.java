package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.GeoDraw;

@RepositoryRestResource
public interface GeoDrawRepository extends PagingAndSortingRepository<GeoDraw, Integer> {

}