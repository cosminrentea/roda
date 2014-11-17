package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Topic;

@RepositoryRestResource
public interface TopicRepository extends PagingAndSortingRepository<Topic, Integer> {

}