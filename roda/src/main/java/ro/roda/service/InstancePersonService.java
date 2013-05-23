package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.InstancePerson;
import ro.roda.domain.InstancePersonPK;

public interface InstancePersonService {

	public abstract long countAllInstancepeople();

	public abstract void deleteInstancePerson(InstancePerson instancePerson);

	public abstract InstancePerson findInstancePerson(InstancePersonPK id);

	public abstract List<InstancePerson> findAllInstancepeople();

	public abstract List<InstancePerson> findInstancePersonEntries(int firstResult, int maxResults);

	public abstract void saveInstancePerson(InstancePerson instancePerson);

	public abstract InstancePerson updateInstancePerson(InstancePerson instancePerson);

}
