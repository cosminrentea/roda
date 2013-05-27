package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.Org;

@Service
@Transactional
public class OrgServiceImpl implements OrgService {

	public long countAllOrgs() {
		return Org.countOrgs();
	}

	public void deleteOrg(Org org) {
		org.remove();
	}

	public Org findOrg(Integer id) {
		return Org.findOrg(id);
	}

	public List<Org> findAllOrgs() {
		return Org.findAllOrgs();
	}

	public List<Org> findOrgEntries(int firstResult, int maxResults) {
		return Org.findOrgEntries(firstResult, maxResults);
	}

	public void saveOrg(Org org) {
		org.persist();
	}

	public Org updateOrg(Org org) {
		return org.merge();
	}
}
