package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.InstanceDescr;
import ro.roda.domain.InstanceDescrPK;


@Service
@Transactional
public class InstanceDescrServiceImpl implements InstanceDescrService {

	public long countAllInstanceDescrs() {
        return InstanceDescr.countInstanceDescrs();
    }

	public void deleteInstanceDescr(InstanceDescr instanceDescr) {
        instanceDescr.remove();
    }

	public InstanceDescr findInstanceDescr(InstanceDescrPK id) {
        return InstanceDescr.findInstanceDescr(id);
    }

	public List<InstanceDescr> findAllInstanceDescrs() {
        return InstanceDescr.findAllInstanceDescrs();
    }

	public List<InstanceDescr> findInstanceDescrEntries(int firstResult, int maxResults) {
        return InstanceDescr.findInstanceDescrEntries(firstResult, maxResults);
    }

	public void saveInstanceDescr(InstanceDescr instanceDescr) {
        instanceDescr.persist();
    }

	public InstanceDescr updateInstanceDescr(InstanceDescr instanceDescr) {
        return instanceDescr.merge();
    }
}
