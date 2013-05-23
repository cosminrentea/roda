package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.OrgInternet;
import ro.roda.domain.OrgInternetPK;

public interface OrgInternetService {

	public abstract long countAllOrgInternets();

	public abstract void deleteOrgInternet(OrgInternet orgInternet);

	public abstract OrgInternet findOrgInternet(OrgInternetPK id);

	public abstract List<OrgInternet> findAllOrgInternets();

	public abstract List<OrgInternet> findOrgInternetEntries(int firstResult, int maxResults);

	public abstract void saveOrgInternet(OrgInternet orgInternet);

	public abstract OrgInternet updateOrgInternet(OrgInternet orgInternet);

}
