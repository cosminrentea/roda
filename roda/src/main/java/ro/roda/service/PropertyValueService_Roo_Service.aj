// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.service;

import java.util.List;
import ro.roda.domain.PropertyValue;
import ro.roda.service.PropertyValueService;

privileged aspect PropertyValueService_Roo_Service {
    
    public abstract long PropertyValueService.countAllPropertyValues();    
    public abstract void PropertyValueService.deletePropertyValue(PropertyValue propertyValue);    
    public abstract PropertyValue PropertyValueService.findPropertyValue(Integer id);    
    public abstract List<PropertyValue> PropertyValueService.findAllPropertyValues();    
    public abstract List<PropertyValue> PropertyValueService.findPropertyValueEntries(int firstResult, int maxResults);    
    public abstract void PropertyValueService.savePropertyValue(PropertyValue propertyValue);    
    public abstract PropertyValue PropertyValueService.updatePropertyValue(PropertyValue propertyValue);    
}