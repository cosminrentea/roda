package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.InstanceVariable;
import ro.roda.domain.InstanceVariablePK;

@Service
@Transactional
public class InstanceVariableServiceImpl implements InstanceVariableService {

	public long countAllInstanceVariables() {
		return InstanceVariable.countInstanceVariables();
	}

	public void deleteInstanceVariable(InstanceVariable instanceVariable) {
		instanceVariable.remove();
	}

	public InstanceVariable findInstanceVariable(InstanceVariablePK id) {
		return InstanceVariable.findInstanceVariable(id);
	}

	public List<InstanceVariable> findAllInstanceVariables() {
		return InstanceVariable.findAllInstanceVariables();
	}

	public List<InstanceVariable> findInstanceVariableEntries(int firstResult, int maxResults) {
		return InstanceVariable.findInstanceVariableEntries(firstResult, maxResults);
	}

	public void saveInstanceVariable(InstanceVariable instanceVariable) {
		instanceVariable.persist();
	}

	public InstanceVariable updateInstanceVariable(InstanceVariable instanceVariable) {
		return instanceVariable.merge();
	}
}
