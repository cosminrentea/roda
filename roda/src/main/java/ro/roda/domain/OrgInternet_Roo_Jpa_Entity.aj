// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import ro.roda.domain.OrgInternet;
import ro.roda.domain.OrgInternetPK;

privileged aspect OrgInternet_Roo_Jpa_Entity {
    
    declare @type: OrgInternet: @Entity;
    
    declare @type: OrgInternet: @Table(schema = "public", name = "org_internet");
    
    @EmbeddedId
    private OrgInternetPK OrgInternet.id;
    
    public OrgInternetPK OrgInternet.getId() {
        return this.id;
    }
    
    public void OrgInternet.setId(OrgInternetPK id) {
        this.id = id;
    }
    
}