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
import org.springframework.stereotype.Component;
import ro.roda.TitleType;
import ro.roda.TitleTypeDataOnDemand;

privileged aspect TitleTypeDataOnDemand_Roo_DataOnDemand {
    
    declare @type: TitleTypeDataOnDemand: @Component;
    
    private Random TitleTypeDataOnDemand.rnd = new SecureRandom();
    
    private List<TitleType> TitleTypeDataOnDemand.data;
    
    public TitleType TitleTypeDataOnDemand.getNewTransientTitleType(int index) {
        TitleType obj = new TitleType();
        setName(obj, index);
        return obj;
    }
    
    public void TitleTypeDataOnDemand.setName(TitleType obj, int index) {
        String name = "name_" + index;
        if (name.length() > 50) {
            name = name.substring(0, 50);
        }
        obj.setName(name);
    }
    
    public TitleType TitleTypeDataOnDemand.getSpecificTitleType(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        TitleType obj = data.get(index);
        Integer id = obj.getId();
        return TitleType.findTitleType(id);
    }
    
    public TitleType TitleTypeDataOnDemand.getRandomTitleType() {
        init();
        TitleType obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return TitleType.findTitleType(id);
    }
    
    public boolean TitleTypeDataOnDemand.modifyTitleType(TitleType obj) {
        return false;
    }
    
    public void TitleTypeDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = TitleType.findTitleTypeEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'TitleType' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<TitleType>();
        for (int i = 0; i < 10; i++) {
            TitleType obj = getNewTransientTitleType(i);
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