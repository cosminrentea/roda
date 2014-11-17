package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.FormEditedNumberVar;

@RepositoryRestResource
public interface FormEditedNumberVarRepository extends PagingAndSortingRepository<FormEditedNumberVar, Integer> {

}