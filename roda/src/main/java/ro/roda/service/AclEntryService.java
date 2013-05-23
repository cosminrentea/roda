package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.AclEntry;


public interface AclEntryService {

	public abstract long countAllAclEntrys();


	public abstract void deleteAclEntry(AclEntry aclEntry);


	public abstract AclEntry findAclEntry(Long id);


	public abstract List<AclEntry> findAllAclEntrys();


	public abstract List<AclEntry> findAclEntryEntries(int firstResult, int maxResults);


	public abstract void saveAclEntry(AclEntry aclEntry);


	public abstract AclEntry updateAclEntry(AclEntry aclEntry);

}
