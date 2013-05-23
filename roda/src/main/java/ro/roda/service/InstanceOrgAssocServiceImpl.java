package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.InstanceOrgAssoc;

@Service
@Transactional
public class InstanceOrgAssocServiceImpl implements InstanceOrgAssocService {

	public long countAllInstanceOrgAssocs() {
		return InstanceOrgAssoc.countInstanceOrgAssocs();
	}

	public void deleteInstanceOrgAssoc(InstanceOrgAssoc instanceOrgAssoc) {
		instanceOrgAssoc.remove();
	}

	public InstanceOrgAssoc findInstanceOrgAssoc(Integer id) {
		return InstanceOrgAssoc.findInstanceOrgAssoc(id);
	}

	public List<InstanceOrgAssoc> findAllInstanceOrgAssocs() {
		return InstanceOrgAssoc.findAllInstanceOrgAssocs();
	}

	public List<InstanceOrgAssoc> findInstanceOrgAssocEntries(int firstResult, int maxResults) {
		return InstanceOrgAssoc.findInstanceOrgAssocEntries(firstResult, maxResults);
	}

	public void saveInstanceOrgAssoc(InstanceOrgAssoc instanceOrgAssoc) {
		instanceOrgAssoc.persist();
	}

	public InstanceOrgAssoc updateInstanceOrgAssoc(InstanceOrgAssoc instanceOrgAssoc) {
		return instanceOrgAssoc.merge();
	}
}
