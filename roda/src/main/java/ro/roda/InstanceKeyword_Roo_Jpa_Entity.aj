// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import ro.roda.InstanceKeyword;
import ro.roda.InstanceKeywordPK;

privileged aspect InstanceKeyword_Roo_Jpa_Entity {
    
    declare @type: InstanceKeyword: @Entity;
    
    declare @type: InstanceKeyword: @Table(schema = "public", name = "instance_keyword");
    
    @EmbeddedId
    private InstanceKeywordPK InstanceKeyword.id;
    
    public InstanceKeywordPK InstanceKeyword.getId() {
        return this.id;
    }
    
    public void InstanceKeyword.setId(InstanceKeywordPK id) {
        this.id = id;
    }
    
}
