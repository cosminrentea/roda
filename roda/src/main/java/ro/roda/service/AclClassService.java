package ro.roda.service;

import java.util.List;

import ro.roda.domain.AclClass;

public interface AclClassService {

	public abstract long countAllAclClasses();

	public abstract void deleteAclClass(AclClass aclClass);

	public abstract AclClass findAclClass(Long id);

	public abstract List<AclClass> findAllAclClasses();

	public abstract List<AclClass> findAclClassEntries(int firstResult, int maxResults);

	public abstract void saveAclClass(AclClass aclClass);

	public abstract AclClass updateAclClass(AclClass aclClass);

}
