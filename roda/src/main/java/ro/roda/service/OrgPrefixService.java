package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.OrgPrefix;


public interface OrgPrefixService {

	public abstract long countAllOrgPrefixes();


	public abstract void deleteOrgPrefix(OrgPrefix orgPrefix);


	public abstract OrgPrefix findOrgPrefix(Integer id);


	public abstract List<OrgPrefix> findAllOrgPrefixes();


	public abstract List<OrgPrefix> findOrgPrefixEntries(int firstResult, int maxResults);


	public abstract void saveOrgPrefix(OrgPrefix orgPrefix);


	public abstract OrgPrefix updateOrgPrefix(OrgPrefix orgPrefix);

}
