package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.FormSelectionVar;
import ro.roda.domain.FormSelectionVarPK;

@Service
@Transactional
public class FormSelectionVarServiceImpl implements FormSelectionVarService {

	public long countAllFormSelectionVars() {
		return FormSelectionVar.countFormSelectionVars();
	}

	public void deleteFormSelectionVar(FormSelectionVar formSelectionVar) {
		formSelectionVar.remove();
	}

	public FormSelectionVar findFormSelectionVar(FormSelectionVarPK id) {
		return FormSelectionVar.findFormSelectionVar(id);
	}

	public List<FormSelectionVar> findAllFormSelectionVars() {
		return FormSelectionVar.findAllFormSelectionVars();
	}

	public List<FormSelectionVar> findFormSelectionVarEntries(int firstResult, int maxResults) {
		return FormSelectionVar.findFormSelectionVarEntries(firstResult, maxResults);
	}

	public void saveFormSelectionVar(FormSelectionVar formSelectionVar) {
		formSelectionVar.persist();
	}

	public FormSelectionVar updateFormSelectionVar(FormSelectionVar formSelectionVar) {
		return formSelectionVar.merge();
	}
}
