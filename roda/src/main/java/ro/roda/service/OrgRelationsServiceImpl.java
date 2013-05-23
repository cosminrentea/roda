package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.OrgRelations;
import ro.roda.domain.OrgRelationsPK;


@Service
@Transactional
public class OrgRelationsServiceImpl implements OrgRelationsService {

	public long countAllOrgRelationses() {
        return OrgRelations.countOrgRelationses();
    }

	public void deleteOrgRelations(OrgRelations orgRelations) {
        orgRelations.remove();
    }

	public OrgRelations findOrgRelations(OrgRelationsPK id) {
        return OrgRelations.findOrgRelations(id);
    }

	public List<OrgRelations> findAllOrgRelationses() {
        return OrgRelations.findAllOrgRelationses();
    }

	public List<OrgRelations> findOrgRelationsEntries(int firstResult, int maxResults) {
        return OrgRelations.findOrgRelationsEntries(firstResult, maxResults);
    }

	public void saveOrgRelations(OrgRelations orgRelations) {
        orgRelations.persist();
    }

	public OrgRelations updateOrgRelations(OrgRelations orgRelations) {
        return orgRelations.merge();
    }
}
