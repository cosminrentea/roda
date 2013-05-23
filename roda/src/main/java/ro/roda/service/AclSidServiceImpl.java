package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.AclSid;

@Service
@Transactional
public class AclSidServiceImpl implements AclSidService {

	public long countAllAclSids() {
		return AclSid.countAclSids();
	}

	public void deleteAclSid(AclSid aclSid) {
		aclSid.remove();
	}

	public AclSid findAclSid(Long id) {
		return AclSid.findAclSid(id);
	}

	public List<AclSid> findAllAclSids() {
		return AclSid.findAllAclSids();
	}

	public List<AclSid> findAclSidEntries(int firstResult, int maxResults) {
		return AclSid.findAclSidEntries(firstResult, maxResults);
	}

	public void saveAclSid(AclSid aclSid) {
		aclSid.persist();
	}

	public AclSid updateAclSid(AclSid aclSid) {
		return aclSid.merge();
	}
}
