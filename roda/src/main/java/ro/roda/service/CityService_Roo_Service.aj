// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.service;

import java.util.List;
import ro.roda.domain.City;
import ro.roda.service.CityService;

privileged aspect CityService_Roo_Service {
    
    public abstract long CityService.countAllCitys();    
    public abstract void CityService.deleteCity(City city);    
    public abstract City CityService.findCity(Integer id);    
    public abstract List<City> CityService.findAllCitys();    
    public abstract List<City> CityService.findCityEntries(int firstResult, int maxResults);    
    public abstract void CityService.saveCity(City city);    
    public abstract City CityService.updateCity(City city);    
}