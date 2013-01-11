// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import ro.roda.Region;
import ro.roda.Regiontype;

privileged aspect Regiontype_Roo_DbManaged {
    
    @OneToMany(mappedBy = "regiontypeId")
    private Set<Region> Regiontype.regions;
    
    @Column(name = "name", columnDefinition = "varchar", length = 150)
    @NotNull
    private String Regiontype.name;
    
    public Set<Region> Regiontype.getRegions() {
        return regions;
    }
    
    public void Regiontype.setRegions(Set<Region> regions) {
        this.regions = regions;
    }
    
    public String Regiontype.getName() {
        return name;
    }
    
    public void Regiontype.setName(String name) {
        this.name = name;
    }
    
}
