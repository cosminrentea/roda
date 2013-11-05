package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.InstancePerson;
import ro.roda.domain.InstancePersonPK;

@Service
@Transactional
public class InstancePersonServiceImpl implements InstancePersonService {

	public long countAllInstancepeople() {
		return InstancePerson.countInstancepeople();
	}

	public void deleteInstancePerson(InstancePerson instancePerson) {
		instancePerson.remove();
	}

	public InstancePerson findInstancePerson(InstancePersonPK id) {
		return InstancePerson.findInstancePerson(id);
	}

	public List<InstancePerson> findAllInstancepeople() {
		return InstancePerson.findAllInstancepeople();
	}

	public List<InstancePerson> findInstancePersonEntries(int firstResult, int maxResults) {
		return InstancePerson.findInstancePersonEntries(firstResult, maxResults);
	}

	public void saveInstancePerson(InstancePerson instancePerson) {
		instancePerson.persist();
	}

	public InstancePerson updateInstancePerson(InstancePerson instancePerson) {
		return instancePerson.merge();
	}
}
