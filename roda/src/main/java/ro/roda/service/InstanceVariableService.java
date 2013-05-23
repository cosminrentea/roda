package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.InstanceVariable;
import ro.roda.domain.InstanceVariablePK;


public interface InstanceVariableService {

	public abstract long countAllInstanceVariables();


	public abstract void deleteInstanceVariable(InstanceVariable instanceVariable);


	public abstract InstanceVariable findInstanceVariable(InstanceVariablePK id);


	public abstract List<InstanceVariable> findAllInstanceVariables();


	public abstract List<InstanceVariable> findInstanceVariableEntries(int firstResult, int maxResults);


	public abstract void saveInstanceVariable(InstanceVariable instanceVariable);


	public abstract InstanceVariable updateInstanceVariable(InstanceVariable instanceVariable);

}
