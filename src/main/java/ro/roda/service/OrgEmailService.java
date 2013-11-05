package ro.roda.service;

import java.util.List;

import ro.roda.domain.OrgEmail;
import ro.roda.domain.OrgEmailPK;

public interface OrgEmailService {

	public abstract long countAllOrgEmails();

	public abstract void deleteOrgEmail(OrgEmail orgEmail);

	public abstract OrgEmail findOrgEmail(OrgEmailPK id);

	public abstract List<OrgEmail> findAllOrgEmails();

	public abstract List<OrgEmail> findOrgEmailEntries(int firstResult, int maxResults);

	public abstract void saveOrgEmail(OrgEmail orgEmail);

	public abstract OrgEmail updateOrgEmail(OrgEmail orgEmail);

}
