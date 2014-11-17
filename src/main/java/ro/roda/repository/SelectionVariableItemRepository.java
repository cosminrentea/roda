package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.SelectionVariableItem;

@RepositoryRestResource
public interface SelectionVariableItemRepository extends PagingAndSortingRepository<SelectionVariableItem, Integer> {

}