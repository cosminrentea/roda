package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Item;

@RepositoryRestResource
public interface ItemRepository extends PagingAndSortingRepository<Item, Integer> {

}