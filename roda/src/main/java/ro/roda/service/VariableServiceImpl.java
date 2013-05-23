package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Variable;


@Service
@Transactional
public class VariableServiceImpl implements VariableService {

	public long countAllVariables() {
        return Variable.countVariables();
    }

	public void deleteVariable(Variable variable) {
        variable.remove();
    }

	public Variable findVariable(Long id) {
        return Variable.findVariable(id);
    }

	public List<Variable> findAllVariables() {
        return Variable.findAllVariables();
    }

	public List<Variable> findVariableEntries(int firstResult, int maxResults) {
        return Variable.findVariableEntries(firstResult, maxResults);
    }

	public void saveVariable(Variable variable) {
        variable.persist();
    }

	public Variable updateVariable(Variable variable) {
        return variable.merge();
    }
}
