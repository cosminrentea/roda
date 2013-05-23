package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.OrgRelationType;

@Service
@Transactional
public class OrgRelationTypeServiceImpl implements OrgRelationTypeService {

	public long countAllOrgRelationTypes() {
		return OrgRelationType.countOrgRelationTypes();
	}

	public void deleteOrgRelationType(OrgRelationType orgRelationType) {
		orgRelationType.remove();
	}

	public OrgRelationType findOrgRelationType(Integer id) {
		return OrgRelationType.findOrgRelationType(id);
	}

	public List<OrgRelationType> findAllOrgRelationTypes() {
		return OrgRelationType.findAllOrgRelationTypes();
	}

	public List<OrgRelationType> findOrgRelationTypeEntries(int firstResult, int maxResults) {
		return OrgRelationType.findOrgRelationTypeEntries(firstResult, maxResults);
	}

	public void saveOrgRelationType(OrgRelationType orgRelationType) {
		orgRelationType.persist();
	}

	public OrgRelationType updateOrgRelationType(OrgRelationType orgRelationType) {
		return orgRelationType.merge();
	}
}
