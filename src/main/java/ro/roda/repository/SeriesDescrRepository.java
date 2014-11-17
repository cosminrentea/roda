package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.SeriesDescr;

@RepositoryRestResource
public interface SeriesDescrRepository extends PagingAndSortingRepository<SeriesDescr, Integer> {

}