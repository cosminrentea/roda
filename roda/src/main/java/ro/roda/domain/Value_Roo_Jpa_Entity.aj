// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import ro.roda.domain.Value;

privileged aspect Value_Roo_Jpa_Entity {
    
    declare @type: Value: @Entity;
    
    declare @type: Value: @Table(schema = "public", name = "value");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id", columnDefinition = "int8")
    private Long Value.itemId;
    
    public Long Value.getItemId() {
        return this.itemId;
    }
    
    public void Value.setItemId(Long id) {
        this.itemId = id;
    }
    
}