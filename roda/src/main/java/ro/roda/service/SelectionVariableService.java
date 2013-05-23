package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.SelectionVariable;


public interface SelectionVariableService {

	public abstract long countAllSelectionVariables();


	public abstract void deleteSelectionVariable(SelectionVariable selectionVariable);


	public abstract SelectionVariable findSelectionVariable(Long id);


	public abstract List<SelectionVariable> findAllSelectionVariables();


	public abstract List<SelectionVariable> findSelectionVariableEntries(int firstResult, int maxResults);


	public abstract void saveSelectionVariable(SelectionVariable selectionVariable);


	public abstract SelectionVariable updateSelectionVariable(SelectionVariable selectionVariable);

}
