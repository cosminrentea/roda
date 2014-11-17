package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.OrgRelationType;

@RepositoryRestResource
public interface OrgRelationTypeRepository extends PagingAndSortingRepository<OrgRelationType, Integer> {

}