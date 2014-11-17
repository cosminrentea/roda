package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.OtherStatistic;

@RepositoryRestResource
public interface OtherStatisticRepository extends PagingAndSortingRepository<OtherStatistic, Integer> {

}