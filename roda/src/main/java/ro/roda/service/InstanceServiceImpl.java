package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Instance;

@Service
@Transactional
public class InstanceServiceImpl implements InstanceService {

	public long countAllInstances() {
		return Instance.countInstances();
	}

	public void deleteInstance(Instance instance) {
		instance.remove();
	}

	public Instance findInstance(Integer id) {
		return Instance.findInstance(id);
	}

	public List<Instance> findAllInstances() {
		return Instance.findAllInstances();
	}

	public List<Instance> findInstanceEntries(int firstResult, int maxResults) {
		return Instance.findInstanceEntries(firstResult, maxResults);
	}

	public void saveInstance(Instance instance) {
		instance.persist();
	}

	public Instance updateInstance(Instance instance) {
		return instance.merge();
	}
}
