// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.GroupAuthorities;
import ro.roda.domain.GroupAuthoritiesPK;
import ro.roda.service.GroupAuthoritiesServiceImpl;

privileged aspect GroupAuthoritiesServiceImpl_Roo_Service {
    
    declare @type: GroupAuthoritiesServiceImpl: @Service;
    
    declare @type: GroupAuthoritiesServiceImpl: @Transactional;
    
    public long GroupAuthoritiesServiceImpl.countAllGroupAuthoritieses() {
        return GroupAuthorities.countGroupAuthoritieses();
    }
    
    public void GroupAuthoritiesServiceImpl.deleteGroupAuthorities(GroupAuthorities groupAuthorities) {
        groupAuthorities.remove();
    }
    
    public GroupAuthorities GroupAuthoritiesServiceImpl.findGroupAuthorities(GroupAuthoritiesPK id) {
        return GroupAuthorities.findGroupAuthorities(id);
    }
    
    public List<GroupAuthorities> GroupAuthoritiesServiceImpl.findAllGroupAuthoritieses() {
        return GroupAuthorities.findAllGroupAuthoritieses();
    }
    
    public List<GroupAuthorities> GroupAuthoritiesServiceImpl.findGroupAuthoritiesEntries(int firstResult, int maxResults) {
        return GroupAuthorities.findGroupAuthoritiesEntries(firstResult, maxResults);
    }
    
    public void GroupAuthoritiesServiceImpl.saveGroupAuthorities(GroupAuthorities groupAuthorities) {
        groupAuthorities.persist();
    }
    
    public GroupAuthorities GroupAuthoritiesServiceImpl.updateGroupAuthorities(GroupAuthorities groupAuthorities) {
        return groupAuthorities.merge();
    }
    
}