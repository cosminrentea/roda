package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.PersonPhone;
import ro.roda.domain.PersonPhonePK;


@Service
@Transactional
public class PersonPhoneServiceImpl implements PersonPhoneService {

	public long countAllPersonPhones() {
        return PersonPhone.countPersonPhones();
    }

	public void deletePersonPhone(PersonPhone personPhone) {
        personPhone.remove();
    }

	public PersonPhone findPersonPhone(PersonPhonePK id) {
        return PersonPhone.findPersonPhone(id);
    }

	public List<PersonPhone> findAllPersonPhones() {
        return PersonPhone.findAllPersonPhones();
    }

	public List<PersonPhone> findPersonPhoneEntries(int firstResult, int maxResults) {
        return PersonPhone.findPersonPhoneEntries(firstResult, maxResults);
    }

	public void savePersonPhone(PersonPhone personPhone) {
        personPhone.persist();
    }

	public PersonPhone updatePersonPhone(PersonPhone personPhone) {
        return personPhone.merge();
    }
}
