package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.PersonAddress;
import ro.roda.domain.PersonAddressPK;

public interface PersonAddressService {

	public abstract long countAllPersonAddresses();

	public abstract void deletePersonAddress(PersonAddress personAddress);

	public abstract PersonAddress findPersonAddress(PersonAddressPK id);

	public abstract List<PersonAddress> findAllPersonAddresses();

	public abstract List<PersonAddress> findPersonAddressEntries(int firstResult, int maxResults);

	public abstract void savePersonAddress(PersonAddress personAddress);

	public abstract PersonAddress updatePersonAddress(PersonAddress personAddress);

}
