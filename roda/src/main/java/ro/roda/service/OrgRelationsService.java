package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.OrgRelations;
import ro.roda.domain.OrgRelationsPK;

public interface OrgRelationsService {

	public abstract long countAllOrgRelationses();

	public abstract void deleteOrgRelations(OrgRelations orgRelations);

	public abstract OrgRelations findOrgRelations(OrgRelationsPK id);

	public abstract List<OrgRelations> findAllOrgRelationses();

	public abstract List<OrgRelations> findOrgRelationsEntries(int firstResult, int maxResults);

	public abstract void saveOrgRelations(OrgRelations orgRelations);

	public abstract OrgRelations updateOrgRelations(OrgRelations orgRelations);

}
