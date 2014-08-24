package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.DdiEditorOrgList;

public interface DdiEditorOrgListService {

	public abstract List<DdiEditorOrgList> findAllOrgs();

	public abstract DdiEditorOrgList findOrg(Integer id);

}
