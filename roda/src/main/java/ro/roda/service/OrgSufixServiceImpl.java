package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.OrgSufix;


@Service
@Transactional
public class OrgSufixServiceImpl implements OrgSufixService {

	public long countAllOrgSufixes() {
        return OrgSufix.countOrgSufixes();
    }

	public void deleteOrgSufix(OrgSufix orgSufix) {
        orgSufix.remove();
    }

	public OrgSufix findOrgSufix(Integer id) {
        return OrgSufix.findOrgSufix(id);
    }

	public List<OrgSufix> findAllOrgSufixes() {
        return OrgSufix.findAllOrgSufixes();
    }

	public List<OrgSufix> findOrgSufixEntries(int firstResult, int maxResults) {
        return OrgSufix.findOrgSufixEntries(firstResult, maxResults);
    }

	public void saveOrgSufix(OrgSufix orgSufix) {
        orgSufix.persist();
    }

	public OrgSufix updateOrgSufix(OrgSufix orgSufix) {
        return orgSufix.merge();
    }
}
