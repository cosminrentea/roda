// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import ro.roda.Scale;

privileged aspect Scale_Roo_Jpa_Entity {
    
    declare @type: Scale: @Entity;
    
    declare @type: Scale: @Table(schema = "public", name = "scale");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id", columnDefinition = "int4")
    private Integer Scale.itemId;
    
    public Integer Scale.getItemId() {
        return this.itemId;
    }
    
    public void Scale.setItemId(Integer id) {
        this.itemId = id;
    }
    
}
