// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import ro.roda.domain.GroupAuthoritiesPK;

privileged aspect GroupAuthoritiesPK_Roo_Identifier {
    
    declare @type: GroupAuthoritiesPK: @Embeddable;
    
    @Column(name = "group_id", columnDefinition = "int8", nullable = false)
    private Long GroupAuthoritiesPK.groupId;
    
    @Column(name = "authority", columnDefinition = "varchar", nullable = false, length = 64)
    private String GroupAuthoritiesPK.authority;
    
    public GroupAuthoritiesPK.new(Long groupId, String authority) {
        super();
        this.groupId = groupId;
        this.authority = authority;
    }

    private GroupAuthoritiesPK.new() {
        super();
    }

    public Long GroupAuthoritiesPK.getGroupId() {
        return groupId;
    }
    
    public String GroupAuthoritiesPK.getAuthority() {
        return authority;
    }
    
}