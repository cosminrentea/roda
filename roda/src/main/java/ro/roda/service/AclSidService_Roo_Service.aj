// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.service;

import java.util.List;
import ro.roda.domain.AclSid;
import ro.roda.service.AclSidService;

privileged aspect AclSidService_Roo_Service {
    
    public abstract long AclSidService.countAllAclSids();    
    public abstract void AclSidService.deleteAclSid(AclSid aclSid);    
    public abstract AclSid AclSidService.findAclSid(Long id);    
    public abstract List<AclSid> AclSidService.findAllAclSids();    
    public abstract List<AclSid> AclSidService.findAclSidEntries(int firstResult, int maxResults);    
    public abstract void AclSidService.saveAclSid(AclSid aclSid);    
    public abstract AclSid AclSidService.updateAclSid(AclSid aclSid);    
}