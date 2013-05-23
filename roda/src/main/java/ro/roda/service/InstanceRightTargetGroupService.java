package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.InstanceRightTargetGroup;
import ro.roda.domain.InstanceRightTargetGroupPK;


public interface InstanceRightTargetGroupService {

	public abstract long countAllInstanceRightTargetGroups();


	public abstract void deleteInstanceRightTargetGroup(InstanceRightTargetGroup instanceRightTargetGroup);


	public abstract InstanceRightTargetGroup findInstanceRightTargetGroup(InstanceRightTargetGroupPK id);


	public abstract List<InstanceRightTargetGroup> findAllInstanceRightTargetGroups();


	public abstract List<InstanceRightTargetGroup> findInstanceRightTargetGroupEntries(int firstResult, int maxResults);


	public abstract void saveInstanceRightTargetGroup(InstanceRightTargetGroup instanceRightTargetGroup);


	public abstract InstanceRightTargetGroup updateInstanceRightTargetGroup(InstanceRightTargetGroup instanceRightTargetGroup);

}
