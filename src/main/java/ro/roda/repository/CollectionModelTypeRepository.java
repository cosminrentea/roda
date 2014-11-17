package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.CollectionModelType;

@RepositoryRestResource
public interface CollectionModelTypeRepository extends PagingAndSortingRepository<CollectionModelType, Integer> {

}