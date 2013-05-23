package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Phone;

@Service
@Transactional
public class PhoneServiceImpl implements PhoneService {

	public long countAllPhones() {
		return Phone.countPhones();
	}

	public void deletePhone(Phone phone) {
		phone.remove();
	}

	public Phone findPhone(Integer id) {
		return Phone.findPhone(id);
	}

	public List<Phone> findAllPhones() {
		return Phone.findAllPhones();
	}

	public List<Phone> findPhoneEntries(int firstResult, int maxResults) {
		return Phone.findPhoneEntries(firstResult, maxResults);
	}

	public void savePhone(Phone phone) {
		phone.persist();
	}

	public Phone updatePhone(Phone phone) {
		return phone.merge();
	}
}
