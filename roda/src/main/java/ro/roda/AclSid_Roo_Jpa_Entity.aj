// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import ro.roda.AclSid;

privileged aspect AclSid_Roo_Jpa_Entity {
    
    declare @type: AclSid: @Entity;
    
    declare @type: AclSid: @Table(schema = "public", name = "acl_sid");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigserial")
    private Long AclSid.id;
    
    public Long AclSid.getId() {
        return this.id;
    }
    
    public void AclSid.setId(Long id) {
        this.id = id;
    }
    
}