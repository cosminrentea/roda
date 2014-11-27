package ro.roda.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.query.Param;

import ro.roda.domain.Keyword;

@RepositoryRestResource
public interface KeywordRepository extends PagingAndSortingRepository<Keyword, Integer> {

	List<Keyword> findByName(@Param("name") String name);

}