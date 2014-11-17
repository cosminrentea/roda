package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Regiontype;

@RepositoryRestResource
public interface RegiontypeRepository extends PagingAndSortingRepository<Regiontype, Integer> {

}