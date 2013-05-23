package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.AclObjectIdentity;


public interface AclObjectIdentityService {

	public abstract long countAllAclObjectIdentitys();


	public abstract void deleteAclObjectIdentity(AclObjectIdentity aclObjectIdentity);


	public abstract AclObjectIdentity findAclObjectIdentity(Long id);


	public abstract List<AclObjectIdentity> findAllAclObjectIdentitys();


	public abstract List<AclObjectIdentity> findAclObjectIdentityEntries(int firstResult, int maxResults);


	public abstract void saveAclObjectIdentity(AclObjectIdentity aclObjectIdentity);


	public abstract AclObjectIdentity updateAclObjectIdentity(AclObjectIdentity aclObjectIdentity);

}
