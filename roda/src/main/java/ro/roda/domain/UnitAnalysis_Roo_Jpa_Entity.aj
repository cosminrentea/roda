// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import ro.roda.domain.UnitAnalysis;

privileged aspect UnitAnalysis_Roo_Jpa_Entity {
    
    declare @type: UnitAnalysis: @Entity;
    
    declare @type: UnitAnalysis: @Table(schema = "public", name = "unit_analysis");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "serial")
    private Integer UnitAnalysis.id;
    
    public Integer UnitAnalysis.getId() {
        return this.id;
    }
    
    public void UnitAnalysis.setId(Integer id) {
        this.id = id;
    }
    
}