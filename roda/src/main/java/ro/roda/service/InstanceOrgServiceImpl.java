package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.InstanceOrg;
import ro.roda.domain.InstanceOrgPK;


@Service
@Transactional
public class InstanceOrgServiceImpl implements InstanceOrgService {

	public long countAllInstanceOrgs() {
        return InstanceOrg.countInstanceOrgs();
    }

	public void deleteInstanceOrg(InstanceOrg instanceOrg) {
        instanceOrg.remove();
    }

	public InstanceOrg findInstanceOrg(InstanceOrgPK id) {
        return InstanceOrg.findInstanceOrg(id);
    }

	public List<InstanceOrg> findAllInstanceOrgs() {
        return InstanceOrg.findAllInstanceOrgs();
    }

	public List<InstanceOrg> findInstanceOrgEntries(int firstResult, int maxResults) {
        return InstanceOrg.findInstanceOrgEntries(firstResult, maxResults);
    }

	public void saveInstanceOrg(InstanceOrg instanceOrg) {
        instanceOrg.persist();
    }

	public InstanceOrg updateInstanceOrg(InstanceOrg instanceOrg) {
        return instanceOrg.merge();
    }
}
