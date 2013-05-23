package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.OrgSufix;

public interface OrgSufixService {

	public abstract long countAllOrgSufixes();

	public abstract void deleteOrgSufix(OrgSufix orgSufix);

	public abstract OrgSufix findOrgSufix(Integer id);

	public abstract List<OrgSufix> findAllOrgSufixes();

	public abstract List<OrgSufix> findOrgSufixEntries(int firstResult, int maxResults);

	public abstract void saveOrgSufix(OrgSufix orgSufix);

	public abstract OrgSufix updateOrgSufix(OrgSufix orgSufix);

}
