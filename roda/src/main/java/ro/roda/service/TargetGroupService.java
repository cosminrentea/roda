package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.TargetGroup;


public interface TargetGroupService {

	public abstract long countAllTargetGroups();


	public abstract void deleteTargetGroup(TargetGroup targetGroup);


	public abstract TargetGroup findTargetGroup(Integer id);


	public abstract List<TargetGroup> findAllTargetGroups();


	public abstract List<TargetGroup> findTargetGroupEntries(int firstResult, int maxResults);


	public abstract void saveTargetGroup(TargetGroup targetGroup);


	public abstract TargetGroup updateTargetGroup(TargetGroup targetGroup);

}
