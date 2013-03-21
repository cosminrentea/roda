// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.service;

import java.util.List;
import ro.roda.domain.OrgPrefix;
import ro.roda.service.OrgPrefixService;

privileged aspect OrgPrefixService_Roo_Service {
    
    public abstract long OrgPrefixService.countAllOrgPrefixes();    
    public abstract void OrgPrefixService.deleteOrgPrefix(OrgPrefix orgPrefix);    
    public abstract OrgPrefix OrgPrefixService.findOrgPrefix(Integer id);    
    public abstract List<OrgPrefix> OrgPrefixService.findAllOrgPrefixes();    
    public abstract List<OrgPrefix> OrgPrefixService.findOrgPrefixEntries(int firstResult, int maxResults);    
    public abstract void OrgPrefixService.saveOrgPrefix(OrgPrefix orgPrefix);    
    public abstract OrgPrefix OrgPrefixService.updateOrgPrefix(OrgPrefix orgPrefix);    
}