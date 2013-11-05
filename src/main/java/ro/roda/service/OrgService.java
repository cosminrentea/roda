package ro.roda.service;

import java.util.List;

import ro.roda.domain.Org;

public interface OrgService {

	public abstract long countAllOrgs();

	public abstract void deleteOrg(Org org);

	public abstract Org findOrg(Integer id);

	public abstract List<Org> findAllOrgs();

	public abstract List<Org> findOrgEntries(int firstResult, int maxResults);

	public abstract void saveOrg(Org org);

	public abstract Org updateOrg(Org org);

}
