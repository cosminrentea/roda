package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.TargetGroup;

@RepositoryRestResource
public interface TargetGroupRepository extends PagingAndSortingRepository<TargetGroup, Integer> {

}