// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import ro.roda.Country;

privileged aspect Country_Roo_Jpa_Entity {
    
    declare @type: Country: @Entity;
    
    declare @type: Country: @Table(schema = "public", name = "country");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "int4")
    private Integer Country.id;
    
    public Integer Country.getId() {
        return this.id;
    }
    
    public void Country.setId(Integer id) {
        this.id = id;
    }
    
}
