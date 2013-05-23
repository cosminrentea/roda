package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.InstanceForm;
import ro.roda.domain.InstanceFormPK;

@Service
@Transactional
public class InstanceFormServiceImpl implements InstanceFormService {

	public long countAllInstanceForms() {
		return InstanceForm.countInstanceForms();
	}

	public void deleteInstanceForm(InstanceForm instanceForm) {
		instanceForm.remove();
	}

	public InstanceForm findInstanceForm(InstanceFormPK id) {
		return InstanceForm.findInstanceForm(id);
	}

	public List<InstanceForm> findAllInstanceForms() {
		return InstanceForm.findAllInstanceForms();
	}

	public List<InstanceForm> findInstanceFormEntries(int firstResult, int maxResults) {
		return InstanceForm.findInstanceFormEntries(firstResult, maxResults);
	}

	public void saveInstanceForm(InstanceForm instanceForm) {
		instanceForm.persist();
	}

	public InstanceForm updateInstanceForm(InstanceForm instanceForm) {
		return instanceForm.merge();
	}
}
