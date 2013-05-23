package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.AclSid;


public interface AclSidService {

	public abstract long countAllAclSids();


	public abstract void deleteAclSid(AclSid aclSid);


	public abstract AclSid findAclSid(Long id);


	public abstract List<AclSid> findAllAclSids();


	public abstract List<AclSid> findAclSidEntries(int firstResult, int maxResults);


	public abstract void saveAclSid(AclSid aclSid);


	public abstract AclSid updateAclSid(AclSid aclSid);

}
