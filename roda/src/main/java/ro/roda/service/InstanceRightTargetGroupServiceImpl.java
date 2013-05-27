package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.InstanceRightTargetGroup;
import ro.roda.domain.InstanceRightTargetGroupPK;

@Service
@Transactional
public class InstanceRightTargetGroupServiceImpl implements InstanceRightTargetGroupService {

	public long countAllInstanceRightTargetGroups() {
		return InstanceRightTargetGroup.countInstanceRightTargetGroups();
	}

	public void deleteInstanceRightTargetGroup(InstanceRightTargetGroup instanceRightTargetGroup) {
		instanceRightTargetGroup.remove();
	}

	public InstanceRightTargetGroup findInstanceRightTargetGroup(InstanceRightTargetGroupPK id) {
		return InstanceRightTargetGroup.findInstanceRightTargetGroup(id);
	}

	public List<InstanceRightTargetGroup> findAllInstanceRightTargetGroups() {
		return InstanceRightTargetGroup.findAllInstanceRightTargetGroups();
	}

	public List<InstanceRightTargetGroup> findInstanceRightTargetGroupEntries(int firstResult, int maxResults) {
		return InstanceRightTargetGroup.findInstanceRightTargetGroupEntries(firstResult, maxResults);
	}

	public void saveInstanceRightTargetGroup(InstanceRightTargetGroup instanceRightTargetGroup) {
		instanceRightTargetGroup.persist();
	}

	public InstanceRightTargetGroup updateInstanceRightTargetGroup(InstanceRightTargetGroup instanceRightTargetGroup) {
		return instanceRightTargetGroup.merge();
	}
}
