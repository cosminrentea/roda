// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import ro.roda.domain.Authorities;
import ro.roda.domain.Users;

privileged aspect Authorities_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
    private Users Authorities.username;
    
    public Users Authorities.getUsername() {
        return username;
    }
    
    public void Authorities.setUsername(Users username) {
        this.username = username;
    }
    
}