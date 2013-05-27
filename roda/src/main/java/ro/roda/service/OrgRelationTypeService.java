package ro.roda.service;

import java.util.List;

import ro.roda.domain.OrgRelationType;

public interface OrgRelationTypeService {

	public abstract long countAllOrgRelationTypes();

	public abstract void deleteOrgRelationType(OrgRelationType orgRelationType);

	public abstract OrgRelationType findOrgRelationType(Integer id);

	public abstract List<OrgRelationType> findAllOrgRelationTypes();

	public abstract List<OrgRelationType> findOrgRelationTypeEntries(int firstResult, int maxResults);

	public abstract void saveOrgRelationType(OrgRelationType orgRelationType);

	public abstract OrgRelationType updateOrgRelationType(OrgRelationType orgRelationType);

}
