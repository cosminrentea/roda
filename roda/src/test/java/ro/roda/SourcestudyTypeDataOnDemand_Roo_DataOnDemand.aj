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
import ro.roda.SourcestudyType;
import ro.roda.SourcestudyTypeDataOnDemand;

privileged aspect SourcestudyTypeDataOnDemand_Roo_DataOnDemand {
    
    declare @type: SourcestudyTypeDataOnDemand: @Component;
    
    private Random SourcestudyTypeDataOnDemand.rnd = new SecureRandom();
    
    private List<SourcestudyType> SourcestudyTypeDataOnDemand.data;
    
    public SourcestudyType SourcestudyTypeDataOnDemand.getNewTransientSourcestudyType(int index) {
        SourcestudyType obj = new SourcestudyType();
        setDescription(obj, index);
        setName(obj, index);
        return obj;
    }
    
    public void SourcestudyTypeDataOnDemand.setDescription(SourcestudyType obj, int index) {
        String description = "description_" + index;
        obj.setDescription(description);
    }
    
    public void SourcestudyTypeDataOnDemand.setName(SourcestudyType obj, int index) {
        String name = "name_" + index;
        if (name.length() > 150) {
            name = name.substring(0, 150);
        }
        obj.setName(name);
    }
    
    public SourcestudyType SourcestudyTypeDataOnDemand.getSpecificSourcestudyType(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        SourcestudyType obj = data.get(index);
        Integer id = obj.getId();
        return SourcestudyType.findSourcestudyType(id);
    }
    
    public SourcestudyType SourcestudyTypeDataOnDemand.getRandomSourcestudyType() {
        init();
        SourcestudyType obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return SourcestudyType.findSourcestudyType(id);
    }
    
    public boolean SourcestudyTypeDataOnDemand.modifySourcestudyType(SourcestudyType obj) {
        return false;
    }
    
    public void SourcestudyTypeDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = SourcestudyType.findSourcestudyTypeEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'SourcestudyType' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<SourcestudyType>();
        for (int i = 0; i < 10; i++) {
            SourcestudyType obj = getNewTransientSourcestudyType(i);
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