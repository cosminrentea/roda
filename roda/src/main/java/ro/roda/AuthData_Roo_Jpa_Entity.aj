// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import ro.roda.AuthData;

privileged aspect AuthData_Roo_Jpa_Entity {
    
    declare @type: AuthData: @Entity;
    
    declare @type: AuthData: @Table(schema = "public", name = "auth_data");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", columnDefinition = "int4")
    private Integer AuthData.userId;
    
    public Integer AuthData.getUserId() {
        return this.userId;
    }
    
    public void AuthData.setUserId(Integer id) {
        this.userId = id;
    }
    
}
