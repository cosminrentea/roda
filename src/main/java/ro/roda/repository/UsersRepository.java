package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Users;

@RepositoryRestResource
public interface UsersRepository extends PagingAndSortingRepository<Users, Integer> {

}