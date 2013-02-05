// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.roda.AclEntry;
import ro.roda.AclEntryDataOnDemand;
import ro.roda.AclObjectIdentityDataOnDemand;
import ro.roda.AclSidDataOnDemand;

privileged aspect AclEntryDataOnDemand_Roo_DataOnDemand {
    
    declare @type: AclEntryDataOnDemand: @Component;
    
    private Random AclEntryDataOnDemand.rnd = new SecureRandom();
    
    private List<AclEntry> AclEntryDataOnDemand.data;
    
    @Autowired
    private AclObjectIdentityDataOnDemand AclEntryDataOnDemand.aclObjectIdentityDataOnDemand;
    
    @Autowired
    private AclSidDataOnDemand AclEntryDataOnDemand.aclSidDataOnDemand;
    
    public AclEntry AclEntryDataOnDemand.getNewTransientAclEntry(int index) {
        AclEntry obj = new AclEntry();
        setAceOrder(obj, index);
        setAuditFailure(obj, index);
        setAuditSuccess(obj, index);
        setGranting(obj, index);
        setMask(obj, index);
        return obj;
    }
    
    public void AclEntryDataOnDemand.setAceOrder(AclEntry obj, int index) {
        Integer aceOrder = new Integer(index);
        obj.setAceOrder(aceOrder);
    }
    
    public void AclEntryDataOnDemand.setAuditFailure(AclEntry obj, int index) {
        Boolean auditFailure = true;
        obj.setAuditFailure(auditFailure);
    }
    
    public void AclEntryDataOnDemand.setAuditSuccess(AclEntry obj, int index) {
        Boolean auditSuccess = true;
        obj.setAuditSuccess(auditSuccess);
    }
    
    public void AclEntryDataOnDemand.setGranting(AclEntry obj, int index) {
        Boolean granting = true;
        obj.setGranting(granting);
    }
    
    public void AclEntryDataOnDemand.setMask(AclEntry obj, int index) {
        Integer mask = new Integer(index);
        obj.setMask(mask);
    }
    
    public AclEntry AclEntryDataOnDemand.getSpecificAclEntry(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        AclEntry obj = data.get(index);
        Long id = obj.getId();
        return AclEntry.findAclEntry(id);
    }
    
    public AclEntry AclEntryDataOnDemand.getRandomAclEntry() {
        init();
        AclEntry obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return AclEntry.findAclEntry(id);
    }
    
    public boolean AclEntryDataOnDemand.modifyAclEntry(AclEntry obj) {
        return false;
    }
    
    public void AclEntryDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = AclEntry.findAclEntryEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'AclEntry' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<AclEntry>();
        for (int i = 0; i < 10; i++) {
            AclEntry obj = getNewTransientAclEntry(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}