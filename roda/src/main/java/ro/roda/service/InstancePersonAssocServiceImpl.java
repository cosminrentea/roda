package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.InstancePersonAssoc;

@Service
@Transactional
public class InstancePersonAssocServiceImpl implements InstancePersonAssocService {

	public long countAllInstancePersonAssocs() {
		return InstancePersonAssoc.countInstancePersonAssocs();
	}

	public void deleteInstancePersonAssoc(InstancePersonAssoc instancePersonAssoc) {
		instancePersonAssoc.remove();
	}

	public InstancePersonAssoc findInstancePersonAssoc(Integer id) {
		return InstancePersonAssoc.findInstancePersonAssoc(id);
	}

	public List<InstancePersonAssoc> findAllInstancePersonAssocs() {
		return InstancePersonAssoc.findAllInstancePersonAssocs();
	}

	public List<InstancePersonAssoc> findInstancePersonAssocEntries(int firstResult, int maxResults) {
		return InstancePersonAssoc.findInstancePersonAssocEntries(firstResult, maxResults);
	}

	public void saveInstancePersonAssoc(InstancePersonAssoc instancePersonAssoc) {
		instancePersonAssoc.persist();
	}

	public InstancePersonAssoc updateInstancePersonAssoc(InstancePersonAssoc instancePersonAssoc) {
		return instancePersonAssoc.merge();
	}
}
