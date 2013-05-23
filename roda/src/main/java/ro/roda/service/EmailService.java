package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.Email;


public interface EmailService {

	public abstract long countAllEmails();


	public abstract void deleteEmail(Email email);


	public abstract Email findEmail(Integer id);


	public abstract List<Email> findAllEmails();


	public abstract List<Email> findEmailEntries(int firstResult, int maxResults);


	public abstract void saveEmail(Email email);


	public abstract Email updateEmail(Email email);

}
