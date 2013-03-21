// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.SourceContacts;
import ro.roda.service.SourceContactsServiceImpl;

privileged aspect SourceContactsServiceImpl_Roo_Service {
    
    declare @type: SourceContactsServiceImpl: @Service;
    
    declare @type: SourceContactsServiceImpl: @Transactional;
    
    public long SourceContactsServiceImpl.countAllSourceContactses() {
        return SourceContacts.countSourceContactses();
    }
    
    public void SourceContactsServiceImpl.deleteSourceContacts(SourceContacts sourceContacts) {
        sourceContacts.remove();
    }
    
    public SourceContacts SourceContactsServiceImpl.findSourceContacts(Integer id) {
        return SourceContacts.findSourceContacts(id);
    }
    
    public List<SourceContacts> SourceContactsServiceImpl.findAllSourceContactses() {
        return SourceContacts.findAllSourceContactses();
    }
    
    public List<SourceContacts> SourceContactsServiceImpl.findSourceContactsEntries(int firstResult, int maxResults) {
        return SourceContacts.findSourceContactsEntries(firstResult, maxResults);
    }
    
    public void SourceContactsServiceImpl.saveSourceContacts(SourceContacts sourceContacts) {
        sourceContacts.persist();
    }
    
    public SourceContacts SourceContactsServiceImpl.updateSourceContacts(SourceContacts sourceContacts) {
        return sourceContacts.merge();
    }
    
}