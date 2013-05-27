package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.Vargroup;

@Service
@Transactional
public class VargroupServiceImpl implements VargroupService {

	public long countAllVargroups() {
		return Vargroup.countVargroups();
	}

	public void deleteVargroup(Vargroup vargroup) {
		vargroup.remove();
	}

	public Vargroup findVargroup(Long id) {
		return Vargroup.findVargroup(id);
	}

	public List<Vargroup> findAllVargroups() {
		return Vargroup.findAllVargroups();
	}

	public List<Vargroup> findVargroupEntries(int firstResult, int maxResults) {
		return Vargroup.findVargroupEntries(firstResult, maxResults);
	}

	public void saveVargroup(Vargroup vargroup) {
		vargroup.persist();
	}

	public Vargroup updateVargroup(Vargroup vargroup) {
		return vargroup.merge();
	}
}
