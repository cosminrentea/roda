package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.OrgAddress;
import ro.roda.domain.OrgAddressPK;

public interface OrgAddressService {

	public abstract long countAllOrgAddresses();

	public abstract void deleteOrgAddress(OrgAddress orgAddress);

	public abstract OrgAddress findOrgAddress(OrgAddressPK id);

	public abstract List<OrgAddress> findAllOrgAddresses();

	public abstract List<OrgAddress> findOrgAddressEntries(int firstResult, int maxResults);

	public abstract void saveOrgAddress(OrgAddress orgAddress);

	public abstract OrgAddress updateOrgAddress(OrgAddress orgAddress);

}
