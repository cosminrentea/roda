// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Phone;
import ro.roda.service.PhoneServiceImpl;

privileged aspect PhoneServiceImpl_Roo_Service {
    
    declare @type: PhoneServiceImpl: @Service;
    
    declare @type: PhoneServiceImpl: @Transactional;
    
    public long PhoneServiceImpl.countAllPhones() {
        return Phone.countPhones();
    }
    
    public void PhoneServiceImpl.deletePhone(Phone phone) {
        phone.remove();
    }
    
    public Phone PhoneServiceImpl.findPhone(Integer id) {
        return Phone.findPhone(id);
    }
    
    public List<Phone> PhoneServiceImpl.findAllPhones() {
        return Phone.findAllPhones();
    }
    
    public List<Phone> PhoneServiceImpl.findPhoneEntries(int firstResult, int maxResults) {
        return Phone.findPhoneEntries(firstResult, maxResults);
    }
    
    public void PhoneServiceImpl.savePhone(Phone phone) {
        phone.persist();
    }
    
    public Phone PhoneServiceImpl.updatePhone(Phone phone) {
        return phone.merge();
    }
    
}