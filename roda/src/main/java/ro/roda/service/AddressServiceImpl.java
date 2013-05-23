package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Address;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

	public long countAllAddresses() {
		return Address.countAddresses();
	}

	public void deleteAddress(Address address) {
		address.remove();
	}

	public Address findAddress(Integer id) {
		return Address.findAddress(id);
	}

	public List<Address> findAllAddresses() {
		return Address.findAllAddresses();
	}

	public List<Address> findAddressEntries(int firstResult, int maxResults) {
		return Address.findAddressEntries(firstResult, maxResults);
	}

	public void saveAddress(Address address) {
		address.persist();
	}

	public Address updateAddress(Address address) {
		return address.merge();
	}
}
