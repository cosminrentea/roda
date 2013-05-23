package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.InstanceRight;


@Service
@Transactional
public class InstanceRightServiceImpl implements InstanceRightService {

	public long countAllInstanceRights() {
        return InstanceRight.countInstanceRights();
    }

	public void deleteInstanceRight(InstanceRight instanceRight) {
        instanceRight.remove();
    }

	public InstanceRight findInstanceRight(Integer id) {
        return InstanceRight.findInstanceRight(id);
    }

	public List<InstanceRight> findAllInstanceRights() {
        return InstanceRight.findAllInstanceRights();
    }

	public List<InstanceRight> findInstanceRightEntries(int firstResult, int maxResults) {
        return InstanceRight.findInstanceRightEntries(firstResult, maxResults);
    }

	public void saveInstanceRight(InstanceRight instanceRight) {
        instanceRight.persist();
    }

	public InstanceRight updateInstanceRight(InstanceRight instanceRight) {
        return instanceRight.merge();
    }
}
