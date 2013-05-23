package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.OrgPrefix;

@Service
@Transactional
public class OrgPrefixServiceImpl implements OrgPrefixService {

	public long countAllOrgPrefixes() {
		return OrgPrefix.countOrgPrefixes();
	}

	public void deleteOrgPrefix(OrgPrefix orgPrefix) {
		orgPrefix.remove();
	}

	public OrgPrefix findOrgPrefix(Integer id) {
		return OrgPrefix.findOrgPrefix(id);
	}

	public List<OrgPrefix> findAllOrgPrefixes() {
		return OrgPrefix.findAllOrgPrefixes();
	}

	public List<OrgPrefix> findOrgPrefixEntries(int firstResult, int maxResults) {
		return OrgPrefix.findOrgPrefixEntries(firstResult, maxResults);
	}

	public void saveOrgPrefix(OrgPrefix orgPrefix) {
		orgPrefix.persist();
	}

	public OrgPrefix updateOrgPrefix(OrgPrefix orgPrefix) {
		return orgPrefix.merge();
	}
}
