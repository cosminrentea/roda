package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.InstanceDescr;
import ro.roda.domain.InstanceDescrPK;

public interface InstanceDescrService {

	public abstract long countAllInstanceDescrs();

	public abstract void deleteInstanceDescr(InstanceDescr instanceDescr);

	public abstract InstanceDescr findInstanceDescr(InstanceDescrPK id);

	public abstract List<InstanceDescr> findAllInstanceDescrs();

	public abstract List<InstanceDescr> findInstanceDescrEntries(int firstResult, int maxResults);

	public abstract void saveInstanceDescr(InstanceDescr instanceDescr);

	public abstract InstanceDescr updateInstanceDescr(InstanceDescr instanceDescr);

}
