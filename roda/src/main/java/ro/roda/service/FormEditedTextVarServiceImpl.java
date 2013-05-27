package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.FormEditedTextVar;
import ro.roda.domain.FormEditedTextVarPK;

@Service
@Transactional
public class FormEditedTextVarServiceImpl implements FormEditedTextVarService {

	public long countAllFormEditedTextVars() {
		return FormEditedTextVar.countFormEditedTextVars();
	}

	public void deleteFormEditedTextVar(FormEditedTextVar formEditedTextVar) {
		formEditedTextVar.remove();
	}

	public FormEditedTextVar findFormEditedTextVar(FormEditedTextVarPK id) {
		return FormEditedTextVar.findFormEditedTextVar(id);
	}

	public List<FormEditedTextVar> findAllFormEditedTextVars() {
		return FormEditedTextVar.findAllFormEditedTextVars();
	}

	public List<FormEditedTextVar> findFormEditedTextVarEntries(int firstResult, int maxResults) {
		return FormEditedTextVar.findFormEditedTextVarEntries(firstResult, maxResults);
	}

	public void saveFormEditedTextVar(FormEditedTextVar formEditedTextVar) {
		formEditedTextVar.persist();
	}

	public FormEditedTextVar updateFormEditedTextVar(FormEditedTextVar formEditedTextVar) {
		return formEditedTextVar.merge();
	}
}
