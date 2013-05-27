package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.FormEditedNumberVar;
import ro.roda.domain.FormEditedNumberVarPK;

@Service
@Transactional
public class FormEditedNumberVarServiceImpl implements FormEditedNumberVarService {

	public long countAllFormEditedNumberVars() {
		return FormEditedNumberVar.countFormEditedNumberVars();
	}

	public void deleteFormEditedNumberVar(FormEditedNumberVar formEditedNumberVar) {
		formEditedNumberVar.remove();
	}

	public FormEditedNumberVar findFormEditedNumberVar(FormEditedNumberVarPK id) {
		return FormEditedNumberVar.findFormEditedNumberVar(id);
	}

	public List<FormEditedNumberVar> findAllFormEditedNumberVars() {
		return FormEditedNumberVar.findAllFormEditedNumberVars();
	}

	public List<FormEditedNumberVar> findFormEditedNumberVarEntries(int firstResult, int maxResults) {
		return FormEditedNumberVar.findFormEditedNumberVarEntries(firstResult, maxResults);
	}

	public void saveFormEditedNumberVar(FormEditedNumberVar formEditedNumberVar) {
		formEditedNumberVar.persist();
	}

	public FormEditedNumberVar updateFormEditedNumberVar(FormEditedNumberVar formEditedNumberVar) {
		return formEditedNumberVar.merge();
	}
}
