// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import ro.roda.Address;
import ro.roda.City;
import ro.roda.Country;
import ro.roda.Region;

privileged aspect City_Roo_DbManaged {
    
    @ManyToMany
    @JoinTable(name = "region_city", joinColumns = { @JoinColumn(name = "city_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "region_id", nullable = false) })
    private Set<Region> City.regions;
    
    @OneToMany(mappedBy = "cityId")
    private Set<Address> City.addresses;
    
    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private Country City.countryId;
    
    @Column(name = "name", columnDefinition = "varchar", length = 100)
    @NotNull
    private String City.name;
    
    public Set<Region> City.getRegions() {
        return regions;
    }
    
    public void City.setRegions(Set<Region> regions) {
        this.regions = regions;
    }
    
    public Set<Address> City.getAddresses() {
        return addresses;
    }
    
    public void City.setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }
    
    public Country City.getCountryId() {
        return countryId;
    }
    
    public void City.setCountryId(Country countryId) {
        this.countryId = countryId;
    }
    
    public String City.getName() {
        return name;
    }
    
    public void City.setName(String name) {
        this.name = name;
    }
    
}
