package ro.roda.service;

import java.util.List;

import ro.roda.domain.Instance;

public interface InstanceService {

	public abstract long countAllInstances();

	public abstract void deleteInstance(Instance instance);

	public abstract Instance findInstance(Integer id);

	public abstract List<Instance> findAllInstances();

	public abstract List<Instance> findInstanceEntries(int firstResult, int maxResults);

	public abstract void saveInstance(Instance instance);

	public abstract Instance updateInstance(Instance instance);

}
