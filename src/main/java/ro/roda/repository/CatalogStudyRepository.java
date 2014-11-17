package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.CatalogStudy;

@RepositoryRestResource
public interface CatalogStudyRepository extends PagingAndSortingRepository<CatalogStudy, Integer> {

}