package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.PersonEmail;
import ro.roda.domain.PersonEmailPK;

public interface PersonEmailService {

	public abstract long countAllPersonEmails();

	public abstract void deletePersonEmail(PersonEmail personEmail);

	public abstract PersonEmail findPersonEmail(PersonEmailPK id);

	public abstract List<PersonEmail> findAllPersonEmails();

	public abstract List<PersonEmail> findPersonEmailEntries(int firstResult, int maxResults);

	public abstract void savePersonEmail(PersonEmail personEmail);

	public abstract PersonEmail updatePersonEmail(PersonEmail personEmail);

}
