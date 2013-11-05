package ro.roda.service;

import java.util.List;

import ro.roda.domain.InstanceOrgAssoc;

public interface InstanceOrgAssocService {

	public abstract long countAllInstanceOrgAssocs();

	public abstract void deleteInstanceOrgAssoc(InstanceOrgAssoc instanceOrgAssoc);

	public abstract InstanceOrgAssoc findInstanceOrgAssoc(Integer id);

	public abstract List<InstanceOrgAssoc> findAllInstanceOrgAssocs();

	public abstract List<InstanceOrgAssoc> findInstanceOrgAssocEntries(int firstResult, int maxResults);

	public abstract void saveInstanceOrgAssoc(InstanceOrgAssoc instanceOrgAssoc);

	public abstract InstanceOrgAssoc updateInstanceOrgAssoc(InstanceOrgAssoc instanceOrgAssoc);

}
