package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Form;

@RepositoryRestResource
public interface FormRepository extends PagingAndSortingRepository<Form, Integer> {

}