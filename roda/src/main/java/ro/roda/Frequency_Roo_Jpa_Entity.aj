// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import ro.roda.Frequency;
import ro.roda.FrequencyPK;

privileged aspect Frequency_Roo_Jpa_Entity {
    
    declare @type: Frequency: @Entity;
    
    declare @type: Frequency: @Table(schema = "public", name = "frequency");
    
    @EmbeddedId
    private FrequencyPK Frequency.id;
    
    public FrequencyPK Frequency.getId() {
        return this.id;
    }
    
    public void Frequency.setId(FrequencyPK id) {
        this.id = id;
    }
    
}
