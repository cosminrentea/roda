package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.InstancePersonAssoc;

public interface InstancePersonAssocService {

	public abstract long countAllInstancePersonAssocs();

	public abstract void deleteInstancePersonAssoc(InstancePersonAssoc instancePersonAssoc);

	public abstract InstancePersonAssoc findInstancePersonAssoc(Integer id);

	public abstract List<InstancePersonAssoc> findAllInstancePersonAssocs();

	public abstract List<InstancePersonAssoc> findInstancePersonAssocEntries(int firstResult, int maxResults);

	public abstract void saveInstancePersonAssoc(InstancePersonAssoc instancePersonAssoc);

	public abstract InstancePersonAssoc updateInstancePersonAssoc(InstancePersonAssoc instancePersonAssoc);

}
