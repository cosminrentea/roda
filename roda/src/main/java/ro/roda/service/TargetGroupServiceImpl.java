package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.TargetGroup;

@Service
@Transactional
public class TargetGroupServiceImpl implements TargetGroupService {

	public long countAllTargetGroups() {
		return TargetGroup.countTargetGroups();
	}

	public void deleteTargetGroup(TargetGroup targetGroup) {
		targetGroup.remove();
	}

	public TargetGroup findTargetGroup(Integer id) {
		return TargetGroup.findTargetGroup(id);
	}

	public List<TargetGroup> findAllTargetGroups() {
		return TargetGroup.findAllTargetGroups();
	}

	public List<TargetGroup> findTargetGroupEntries(int firstResult, int maxResults) {
		return TargetGroup.findTargetGroupEntries(firstResult, maxResults);
	}

	public void saveTargetGroup(TargetGroup targetGroup) {
		targetGroup.persist();
	}

	public TargetGroup updateTargetGroup(TargetGroup targetGroup) {
		return targetGroup.merge();
	}
}
