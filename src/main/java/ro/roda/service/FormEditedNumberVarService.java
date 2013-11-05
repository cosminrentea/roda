package ro.roda.service;

import java.util.List;

import ro.roda.domain.FormEditedNumberVar;
import ro.roda.domain.FormEditedNumberVarPK;

public interface FormEditedNumberVarService {

	public abstract long countAllFormEditedNumberVars();

	public abstract void deleteFormEditedNumberVar(FormEditedNumberVar formEditedNumberVar);

	public abstract FormEditedNumberVar findFormEditedNumberVar(FormEditedNumberVarPK id);

	public abstract List<FormEditedNumberVar> findAllFormEditedNumberVars();

	public abstract List<FormEditedNumberVar> findFormEditedNumberVarEntries(int firstResult, int maxResults);

	public abstract void saveFormEditedNumberVar(FormEditedNumberVar formEditedNumberVar);

	public abstract FormEditedNumberVar updateFormEditedNumberVar(FormEditedNumberVar formEditedNumberVar);

}
