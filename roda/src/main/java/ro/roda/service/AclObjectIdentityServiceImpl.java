package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.AclObjectIdentity;

@Service
@Transactional
public class AclObjectIdentityServiceImpl implements AclObjectIdentityService {

	public long countAllAclObjectIdentitys() {
		return AclObjectIdentity.countAclObjectIdentitys();
	}

	public void deleteAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
		aclObjectIdentity.remove();
	}

	public AclObjectIdentity findAclObjectIdentity(Long id) {
		return AclObjectIdentity.findAclObjectIdentity(id);
	}

	public List<AclObjectIdentity> findAllAclObjectIdentitys() {
		return AclObjectIdentity.findAllAclObjectIdentitys();
	}

	public List<AclObjectIdentity> findAclObjectIdentityEntries(int firstResult, int maxResults) {
		return AclObjectIdentity.findAclObjectIdentityEntries(firstResult, maxResults);
	}

	public void saveAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
		aclObjectIdentity.persist();
	}

	public AclObjectIdentity updateAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
		return aclObjectIdentity.merge();
	}
}
