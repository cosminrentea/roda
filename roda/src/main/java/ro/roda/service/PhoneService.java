package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.Phone;

public interface PhoneService {

	public abstract long countAllPhones();

	public abstract void deletePhone(Phone phone);

	public abstract Phone findPhone(Integer id);

	public abstract List<Phone> findAllPhones();

	public abstract List<Phone> findPhoneEntries(int firstResult, int maxResults);

	public abstract void savePhone(Phone phone);

	public abstract Phone updatePhone(Phone phone);

}
