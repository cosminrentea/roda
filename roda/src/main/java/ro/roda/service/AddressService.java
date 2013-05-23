package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.Address;

public interface AddressService {

	public abstract long countAllAddresses();

	public abstract void deleteAddress(Address address);

	public abstract Address findAddress(Integer id);

	public abstract List<Address> findAllAddresses();

	public abstract List<Address> findAddressEntries(int firstResult, int maxResults);

	public abstract void saveAddress(Address address);

	public abstract Address updateAddress(Address address);

}
