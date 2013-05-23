package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.AclClass;

@Service
@Transactional
public class AclClassServiceImpl implements AclClassService {

	public long countAllAclClasses() {
		return AclClass.countAclClasses();
	}

	public void deleteAclClass(AclClass aclClass) {
		aclClass.remove();
	}

	public AclClass findAclClass(Long id) {
		return AclClass.findAclClass(id);
	}

	public List<AclClass> findAllAclClasses() {
		return AclClass.findAllAclClasses();
	}

	public List<AclClass> findAclClassEntries(int firstResult, int maxResults) {
		return AclClass.findAclClassEntries(firstResult, maxResults);
	}

	public void saveAclClass(AclClass aclClass) {
		aclClass.persist();
	}

	public AclClass updateAclClass(AclClass aclClass) {
		return aclClass.merge();
	}
}
