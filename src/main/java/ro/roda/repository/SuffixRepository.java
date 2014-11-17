package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Suffix;

@RepositoryRestResource
public interface SuffixRepository extends PagingAndSortingRepository<Suffix, Integer> {

}