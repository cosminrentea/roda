package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.AclEntry;


@Service
@Transactional
public class AclEntryServiceImpl implements AclEntryService {

	public long countAllAclEntrys() {
        return AclEntry.countAclEntrys();
    }

	public void deleteAclEntry(AclEntry aclEntry) {
        aclEntry.remove();
    }

	public AclEntry findAclEntry(Long id) {
        return AclEntry.findAclEntry(id);
    }

	public List<AclEntry> findAllAclEntrys() {
        return AclEntry.findAllAclEntrys();
    }

	public List<AclEntry> findAclEntryEntries(int firstResult, int maxResults) {
        return AclEntry.findAclEntryEntries(firstResult, maxResults);
    }

	public void saveAclEntry(AclEntry aclEntry) {
        aclEntry.persist();
    }

	public AclEntry updateAclEntry(AclEntry aclEntry) {
        return aclEntry.merge();
    }
}
