// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.service;

import java.util.List;
import ro.roda.domain.OrgSufix;
import ro.roda.service.OrgSufixService;

privileged aspect OrgSufixService_Roo_Service {
    
    public abstract long OrgSufixService.countAllOrgSufixes();    
    public abstract void OrgSufixService.deleteOrgSufix(OrgSufix orgSufix);    
    public abstract OrgSufix OrgSufixService.findOrgSufix(Integer id);    
    public abstract List<OrgSufix> OrgSufixService.findAllOrgSufixes();    
    public abstract List<OrgSufix> OrgSufixService.findOrgSufixEntries(int firstResult, int maxResults);    
    public abstract void OrgSufixService.saveOrgSufix(OrgSufix orgSufix);    
    public abstract OrgSufix OrgSufixService.updateOrgSufix(OrgSufix orgSufix);    
}