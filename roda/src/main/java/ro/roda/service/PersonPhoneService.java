package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.PersonPhone;
import ro.roda.domain.PersonPhonePK;

public interface PersonPhoneService {

	public abstract long countAllPersonPhones();

	public abstract void deletePersonPhone(PersonPhone personPhone);

	public abstract PersonPhone findPersonPhone(PersonPhonePK id);

	public abstract List<PersonPhone> findAllPersonPhones();

	public abstract List<PersonPhone> findPersonPhoneEntries(int firstResult, int maxResults);

	public abstract void savePersonPhone(PersonPhone personPhone);

	public abstract PersonPhone updatePersonPhone(PersonPhone personPhone);

}
