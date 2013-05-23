package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.InstanceRightValue;

@Service
@Transactional
public class InstanceRightValueServiceImpl implements InstanceRightValueService {

	public long countAllInstanceRightValues() {
		return InstanceRightValue.countInstanceRightValues();
	}

	public void deleteInstanceRightValue(InstanceRightValue instanceRightValue) {
		instanceRightValue.remove();
	}

	public InstanceRightValue findInstanceRightValue(Integer id) {
		return InstanceRightValue.findInstanceRightValue(id);
	}

	public List<InstanceRightValue> findAllInstanceRightValues() {
		return InstanceRightValue.findAllInstanceRightValues();
	}

	public List<InstanceRightValue> findInstanceRightValueEntries(int firstResult, int maxResults) {
		return InstanceRightValue.findInstanceRightValueEntries(firstResult, maxResults);
	}

	public void saveInstanceRightValue(InstanceRightValue instanceRightValue) {
		instanceRightValue.persist();
	}

	public InstanceRightValue updateInstanceRightValue(InstanceRightValue instanceRightValue) {
		return instanceRightValue.merge();
	}
}
