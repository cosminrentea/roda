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
import ro.roda.InternetDataOnDemand;
import ro.roda.OrgDataOnDemand;
import ro.roda.OrgInternet;
import ro.roda.OrgInternetDataOnDemand;
import ro.roda.OrgInternetPK;

privileged aspect OrgInternetDataOnDemand_Roo_DataOnDemand {
    
    declare @type: OrgInternetDataOnDemand: @Component;
    
    private Random OrgInternetDataOnDemand.rnd = new SecureRandom();
    
    private List<OrgInternet> OrgInternetDataOnDemand.data;
    
    @Autowired
    private InternetDataOnDemand OrgInternetDataOnDemand.internetDataOnDemand;
    
    @Autowired
    private OrgDataOnDemand OrgInternetDataOnDemand.orgDataOnDemand;
    
    public OrgInternet OrgInternetDataOnDemand.getNewTransientOrgInternet(int index) {
        OrgInternet obj = new OrgInternet();
        setEmbeddedIdClass(obj, index);
        setMain(obj, index);
        return obj;
    }
    
    public void OrgInternetDataOnDemand.setEmbeddedIdClass(OrgInternet obj, int index) {
        Integer orgId = new Integer(index);
        Integer internetId = new Integer(index);
        
        OrgInternetPK embeddedIdClass = new OrgInternetPK(orgId, internetId);
        obj.setId(embeddedIdClass);
    }
    
    public void OrgInternetDataOnDemand.setMain(OrgInternet obj, int index) {
        Boolean main = true;
        obj.setMain(main);
    }
    
    public OrgInternet OrgInternetDataOnDemand.getSpecificOrgInternet(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        OrgInternet obj = data.get(index);
        OrgInternetPK id = obj.getId();
        return OrgInternet.findOrgInternet(id);
    }
    
    public OrgInternet OrgInternetDataOnDemand.getRandomOrgInternet() {
        init();
        OrgInternet obj = data.get(rnd.nextInt(data.size()));
        OrgInternetPK id = obj.getId();
        return OrgInternet.findOrgInternet(id);
    }
    
    public boolean OrgInternetDataOnDemand.modifyOrgInternet(OrgInternet obj) {
        return false;
    }
    
    public void OrgInternetDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = OrgInternet.findOrgInternetEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'OrgInternet' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<OrgInternet>();
        for (int i = 0; i < 10; i++) {
            OrgInternet obj = getNewTransientOrgInternet(i);
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