package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.PersonAddress;
import ro.roda.domain.PersonAddressPK;

@Service
@Transactional
public class PersonAddressServiceImpl implements PersonAddressService {

	public long countAllPersonAddresses() {
		return PersonAddress.countPersonAddresses();
	}

	public void deletePersonAddress(PersonAddress personAddress) {
		personAddress.remove();
	}

	public PersonAddress findPersonAddress(PersonAddressPK id) {
		return PersonAddress.findPersonAddress(id);
	}

	public List<PersonAddress> findAllPersonAddresses() {
		return PersonAddress.findAllPersonAddresses();
	}

	public List<PersonAddress> findPersonAddressEntries(int firstResult, int maxResults) {
		return PersonAddress.findPersonAddressEntries(firstResult, maxResults);
	}

	public void savePersonAddress(PersonAddress personAddress) {
		personAddress.persist();
	}

	public PersonAddress updatePersonAddress(PersonAddress personAddress) {
		return personAddress.merge();
	}
}
