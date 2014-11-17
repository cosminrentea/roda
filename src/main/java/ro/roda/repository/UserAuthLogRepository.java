package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.UserAuthLog;

@RepositoryRestResource
public interface UserAuthLogRepository extends PagingAndSortingRepository<UserAuthLog, Integer> {

}