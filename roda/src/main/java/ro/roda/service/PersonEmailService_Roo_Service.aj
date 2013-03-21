// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.service;

import java.util.List;
import ro.roda.domain.PersonEmail;
import ro.roda.domain.PersonEmailPK;
import ro.roda.service.PersonEmailService;

privileged aspect PersonEmailService_Roo_Service {
    
    public abstract long PersonEmailService.countAllPersonEmails();    
    public abstract void PersonEmailService.deletePersonEmail(PersonEmail personEmail);    
    public abstract PersonEmail PersonEmailService.findPersonEmail(PersonEmailPK id);    
    public abstract List<PersonEmail> PersonEmailService.findAllPersonEmails();    
    public abstract List<PersonEmail> PersonEmailService.findPersonEmailEntries(int firstResult, int maxResults);    
    public abstract void PersonEmailService.savePersonEmail(PersonEmail personEmail);    
    public abstract PersonEmail PersonEmailService.updatePersonEmail(PersonEmail personEmail);    
}