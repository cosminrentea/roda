package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.News;

@RepositoryRestResource
public interface NewsRepository extends PagingAndSortingRepository<News, Integer> {

}