package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.PersonEmail;
import ro.roda.domain.PersonEmailPK;


@Service
@Transactional
public class PersonEmailServiceImpl implements PersonEmailService {

	public long countAllPersonEmails() {
        return PersonEmail.countPersonEmails();
    }

	public void deletePersonEmail(PersonEmail personEmail) {
        personEmail.remove();
    }

	public PersonEmail findPersonEmail(PersonEmailPK id) {
        return PersonEmail.findPersonEmail(id);
    }

	public List<PersonEmail> findAllPersonEmails() {
        return PersonEmail.findAllPersonEmails();
    }

	public List<PersonEmail> findPersonEmailEntries(int firstResult, int maxResults) {
        return PersonEmail.findPersonEmailEntries(firstResult, maxResults);
    }

	public void savePersonEmail(PersonEmail personEmail) {
        personEmail.persist();
    }

	public PersonEmail updatePersonEmail(PersonEmail personEmail) {
        return personEmail.merge();
    }
}
