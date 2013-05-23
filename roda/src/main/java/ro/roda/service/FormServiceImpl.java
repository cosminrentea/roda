package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Form;

@Service
@Transactional
public class FormServiceImpl implements FormService {

	public long countAllForms() {
		return Form.countForms();
	}

	public void deleteForm(Form form) {
		form.remove();
	}

	public Form findForm(Long id) {
		return Form.findForm(id);
	}

	public List<Form> findAllForms() {
		return Form.findAllForms();
	}

	public List<Form> findFormEntries(int firstResult, int maxResults) {
		return Form.findFormEntries(firstResult, maxResults);
	}

	public void saveForm(Form form) {
		form.persist();
	}

	public Form updateForm(Form form) {
		return form.merge();
	}
}
