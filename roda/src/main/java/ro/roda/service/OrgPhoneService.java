package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.OrgPhone;
import ro.roda.domain.OrgPhonePK;

public interface OrgPhoneService {

	public abstract long countAllOrgPhones();

	public abstract void deleteOrgPhone(OrgPhone orgPhone);

	public abstract OrgPhone findOrgPhone(OrgPhonePK id);

	public abstract List<OrgPhone> findAllOrgPhones();

	public abstract List<OrgPhone> findOrgPhoneEntries(int firstResult, int maxResults);

	public abstract void saveOrgPhone(OrgPhone orgPhone);

	public abstract OrgPhone updateOrgPhone(OrgPhone orgPhone);

}
