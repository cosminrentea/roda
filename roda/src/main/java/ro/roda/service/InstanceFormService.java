package ro.roda.service;

import java.util.List;

import ro.roda.domain.InstanceForm;
import ro.roda.domain.InstanceFormPK;

public interface InstanceFormService {

	public abstract long countAllInstanceForms();

	public abstract void deleteInstanceForm(InstanceForm instanceForm);

	public abstract InstanceForm findInstanceForm(InstanceFormPK id);

	public abstract List<InstanceForm> findAllInstanceForms();

	public abstract List<InstanceForm> findInstanceFormEntries(int firstResult, int maxResults);

	public abstract void saveInstanceForm(InstanceForm instanceForm);

	public abstract InstanceForm updateInstanceForm(InstanceForm instanceForm);

}
