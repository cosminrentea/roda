// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import ro.roda.TitleType;

privileged aspect TitleType_Roo_Jpa_Entity {
    
    declare @type: TitleType: @Entity;
    
    declare @type: TitleType: @Table(schema = "public", name = "title_type");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "int4")
    private Integer TitleType.id;
    
    public Integer TitleType.getId() {
        return this.id;
    }
    
    public void TitleType.setId(Integer id) {
        this.id = id;
    }
    
}
