package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.DdiEditorOrgList;

@Service
@Transactional
public class DdiEditorOrgListServiceImpl implements DdiEditorOrgListService {

	public List<DdiEditorOrgList> findAllOrgs() {
		return DdiEditorOrgList.findAllOrgs();
	}

	public DdiEditorOrgList findOrg(Integer id) {
		return DdiEditorOrgList.findOrg(id);
	}
}
