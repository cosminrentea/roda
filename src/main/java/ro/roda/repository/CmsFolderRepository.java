package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.CmsFolder;

@RepositoryRestResource
public interface CmsFolderRepository extends PagingAndSortingRepository<CmsFolder, Integer> {

}