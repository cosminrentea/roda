package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.OrgPhone;
import ro.roda.domain.OrgPhonePK;

@Service
@Transactional
public class OrgPhoneServiceImpl implements OrgPhoneService {

	public long countAllOrgPhones() {
		return OrgPhone.countOrgPhones();
	}

	public void deleteOrgPhone(OrgPhone orgPhone) {
		orgPhone.remove();
	}

	public OrgPhone findOrgPhone(OrgPhonePK id) {
		return OrgPhone.findOrgPhone(id);
	}

	public List<OrgPhone> findAllOrgPhones() {
		return OrgPhone.findAllOrgPhones();
	}

	public List<OrgPhone> findOrgPhoneEntries(int firstResult, int maxResults) {
		return OrgPhone.findOrgPhoneEntries(firstResult, maxResults);
	}

	public void saveOrgPhone(OrgPhone orgPhone) {
		orgPhone.persist();
	}

	public OrgPhone updateOrgPhone(OrgPhone orgPhone) {
		return orgPhone.merge();
	}
}
