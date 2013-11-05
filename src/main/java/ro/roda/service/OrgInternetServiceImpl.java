package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.OrgInternet;
import ro.roda.domain.OrgInternetPK;

@Service
@Transactional
public class OrgInternetServiceImpl implements OrgInternetService {

	public long countAllOrgInternets() {
		return OrgInternet.countOrgInternets();
	}

	public void deleteOrgInternet(OrgInternet orgInternet) {
		orgInternet.remove();
	}

	public OrgInternet findOrgInternet(OrgInternetPK id) {
		return OrgInternet.findOrgInternet(id);
	}

	public List<OrgInternet> findAllOrgInternets() {
		return OrgInternet.findAllOrgInternets();
	}

	public List<OrgInternet> findOrgInternetEntries(int firstResult, int maxResults) {
		return OrgInternet.findOrgInternetEntries(firstResult, maxResults);
	}

	public void saveOrgInternet(OrgInternet orgInternet) {
		orgInternet.persist();
	}

	public OrgInternet updateOrgInternet(OrgInternet orgInternet) {
		return orgInternet.merge();
	}
}
