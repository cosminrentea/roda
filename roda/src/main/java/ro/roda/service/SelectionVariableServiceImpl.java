package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.SelectionVariable;

@Service
@Transactional
public class SelectionVariableServiceImpl implements SelectionVariableService {

	public long countAllSelectionVariables() {
		return SelectionVariable.countSelectionVariables();
	}

	public void deleteSelectionVariable(SelectionVariable selectionVariable) {
		selectionVariable.remove();
	}

	public SelectionVariable findSelectionVariable(Long id) {
		return SelectionVariable.findSelectionVariable(id);
	}

	public List<SelectionVariable> findAllSelectionVariables() {
		return SelectionVariable.findAllSelectionVariables();
	}

	public List<SelectionVariable> findSelectionVariableEntries(int firstResult, int maxResults) {
		return SelectionVariable.findSelectionVariableEntries(firstResult, maxResults);
	}

	public void saveSelectionVariable(SelectionVariable selectionVariable) {
		selectionVariable.persist();
	}

	public SelectionVariable updateSelectionVariable(SelectionVariable selectionVariable) {
		return selectionVariable.merge();
	}
}
