package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.FormEditedTextVar;

@RepositoryRestResource
public interface FormEditedTextVarRepository extends PagingAndSortingRepository<FormEditedTextVar, Integer> {

}