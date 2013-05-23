package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Email;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {

	public long countAllEmails() {
		return Email.countEmails();
	}

	public void deleteEmail(Email email) {
		email.remove();
	}

	public Email findEmail(Integer id) {
		return Email.findEmail(id);
	}

	public List<Email> findAllEmails() {
		return Email.findAllEmails();
	}

	public List<Email> findEmailEntries(int firstResult, int maxResults) {
		return Email.findEmailEntries(firstResult, maxResults);
	}

	public void saveEmail(Email email) {
		email.persist();
	}

	public Email updateEmail(Email email) {
		return email.merge();
	}
}
