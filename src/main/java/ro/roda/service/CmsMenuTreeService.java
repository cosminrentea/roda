package ro.roda.service;

import java.util.List;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import ro.roda.domainjson.CmsMenuTree;

public interface CmsMenuTreeService {

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, 'read')")
	public abstract List<CmsMenuTree> findAllCmsMenuTrees();

	public abstract CmsMenuTree findCmsMenuTree(Integer id);

}
