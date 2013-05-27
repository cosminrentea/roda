package ro.roda.service;

import java.util.List;

import ro.roda.domain.Form;

public interface FormService {

	public abstract long countAllForms();

	public abstract void deleteForm(Form form);

	public abstract Form findForm(Long id);

	public abstract List<Form> findAllForms();

	public abstract List<Form> findFormEntries(int firstResult, int maxResults);

	public abstract void saveForm(Form form);

	public abstract Form updateForm(Form form);

}
