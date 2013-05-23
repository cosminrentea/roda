package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.FormSelectionVar;
import ro.roda.domain.FormSelectionVarPK;

public interface FormSelectionVarService {

	public abstract long countAllFormSelectionVars();

	public abstract void deleteFormSelectionVar(FormSelectionVar formSelectionVar);

	public abstract FormSelectionVar findFormSelectionVar(FormSelectionVarPK id);

	public abstract List<FormSelectionVar> findAllFormSelectionVars();

	public abstract List<FormSelectionVar> findFormSelectionVarEntries(int firstResult, int maxResults);

	public abstract void saveFormSelectionVar(FormSelectionVar formSelectionVar);

	public abstract FormSelectionVar updateFormSelectionVar(FormSelectionVar formSelectionVar);

}
