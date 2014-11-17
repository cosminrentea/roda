package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.Setting;

@RepositoryRestResource
public interface SettingRepository extends PagingAndSortingRepository<Setting, Integer> {

}