package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.OrgAddress;
import ro.roda.domain.OrgAddressPK;


@Service
@Transactional
public class OrgAddressServiceImpl implements OrgAddressService {

	public long countAllOrgAddresses() {
        return OrgAddress.countOrgAddresses();
    }

	public void deleteOrgAddress(OrgAddress orgAddress) {
        orgAddress.remove();
    }

	public OrgAddress findOrgAddress(OrgAddressPK id) {
        return OrgAddress.findOrgAddress(id);
    }

	public List<OrgAddress> findAllOrgAddresses() {
        return OrgAddress.findAllOrgAddresses();
    }

	public List<OrgAddress> findOrgAddressEntries(int firstResult, int maxResults) {
        return OrgAddress.findOrgAddressEntries(firstResult, maxResults);
    }

	public void saveOrgAddress(OrgAddress orgAddress) {
        orgAddress.persist();
    }

	public OrgAddress updateOrgAddress(OrgAddress orgAddress) {
        return orgAddress.merge();
    }
}
