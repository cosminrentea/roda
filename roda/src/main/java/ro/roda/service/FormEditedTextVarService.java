package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.FormEditedTextVar;
import ro.roda.domain.FormEditedTextVarPK;

public interface FormEditedTextVarService {

	public abstract long countAllFormEditedTextVars();

	public abstract void deleteFormEditedTextVar(FormEditedTextVar formEditedTextVar);

	public abstract FormEditedTextVar findFormEditedTextVar(FormEditedTextVarPK id);

	public abstract List<FormEditedTextVar> findAllFormEditedTextVars();

	public abstract List<FormEditedTextVar> findFormEditedTextVarEntries(int firstResult, int maxResults);

	public abstract void saveFormEditedTextVar(FormEditedTextVar formEditedTextVar);

	public abstract FormEditedTextVar updateFormEditedTextVar(FormEditedTextVar formEditedTextVar);

}
