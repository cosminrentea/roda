package ro.roda.service;

import java.util.List;

import ro.roda.domain.Variable;

public interface VariableService {

	public abstract long countAllVariables();

	public abstract void deleteVariable(Variable variable);

	public abstract Variable findVariable(Long id);

	public abstract List<Variable> findAllVariables();

	public abstract List<Variable> findVariableEntries(int firstResult, int maxResults);

	public abstract void saveVariable(Variable variable);

	public abstract Variable updateVariable(Variable variable);

}
