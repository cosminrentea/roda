package ro.roda.service;

import java.util.List;

import ro.roda.domain.InstanceOrg;
import ro.roda.domain.InstanceOrgPK;

public interface InstanceOrgService {

	public abstract long countAllInstanceOrgs();

	public abstract void deleteInstanceOrg(InstanceOrg instanceOrg);

	public abstract InstanceOrg findInstanceOrg(InstanceOrgPK id);

	public abstract List<InstanceOrg> findAllInstanceOrgs();

	public abstract List<InstanceOrg> findInstanceOrgEntries(int firstResult, int maxResults);

	public abstract void saveInstanceOrg(InstanceOrg instanceOrg);

	public abstract InstanceOrg updateInstanceOrg(InstanceOrg instanceOrg);

}
