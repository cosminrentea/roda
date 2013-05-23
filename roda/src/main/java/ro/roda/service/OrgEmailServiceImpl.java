package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.OrgEmail;
import ro.roda.domain.OrgEmailPK;


@Service
@Transactional
public class OrgEmailServiceImpl implements OrgEmailService {

	public long countAllOrgEmails() {
        return OrgEmail.countOrgEmails();
    }

	public void deleteOrgEmail(OrgEmail orgEmail) {
        orgEmail.remove();
    }

	public OrgEmail findOrgEmail(OrgEmailPK id) {
        return OrgEmail.findOrgEmail(id);
    }

	public List<OrgEmail> findAllOrgEmails() {
        return OrgEmail.findAllOrgEmails();
    }

	public List<OrgEmail> findOrgEmailEntries(int firstResult, int maxResults) {
        return OrgEmail.findOrgEmailEntries(firstResult, maxResults);
    }

	public void saveOrgEmail(OrgEmail orgEmail) {
        orgEmail.persist();
    }

	public OrgEmail updateOrgEmail(OrgEmail orgEmail) {
        return orgEmail.merge();
    }
}
