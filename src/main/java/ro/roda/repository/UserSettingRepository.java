package ro.roda.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ro.roda.domain.UserSetting;

@RepositoryRestResource
public interface UserSettingRepository extends PagingAndSortingRepository<UserSetting, Integer> {

}