package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.InstanceRight;


public interface InstanceRightService {

	public abstract long countAllInstanceRights();


	public abstract void deleteInstanceRight(InstanceRight instanceRight);


	public abstract InstanceRight findInstanceRight(Integer id);


	public abstract List<InstanceRight> findAllInstanceRights();


	public abstract List<InstanceRight> findInstanceRightEntries(int firstResult, int maxResults);


	public abstract void saveInstanceRight(InstanceRight instanceRight);


	public abstract InstanceRight updateInstanceRight(InstanceRight instanceRight);

}
