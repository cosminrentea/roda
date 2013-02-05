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
import ro.roda.CmsLayout;
import ro.roda.CmsLayoutDataOnDemand;
import ro.roda.CmsLayoutGroupDataOnDemand;

privileged aspect CmsLayoutDataOnDemand_Roo_DataOnDemand {
    
    declare @type: CmsLayoutDataOnDemand: @Component;
    
    private Random CmsLayoutDataOnDemand.rnd = new SecureRandom();
    
    private List<CmsLayout> CmsLayoutDataOnDemand.data;
    
    @Autowired
    private CmsLayoutGroupDataOnDemand CmsLayoutDataOnDemand.cmsLayoutGroupDataOnDemand;
    
    public CmsLayout CmsLayoutDataOnDemand.getNewTransientCmsLayout(int index) {
        CmsLayout obj = new CmsLayout();
        setLayoutContent(obj, index);
        setName(obj, index);
        return obj;
    }
    
    public void CmsLayoutDataOnDemand.setLayoutContent(CmsLayout obj, int index) {
        String layoutContent = "layoutContent_" + index;
        obj.setLayoutContent(layoutContent);
    }
    
    public void CmsLayoutDataOnDemand.setName(CmsLayout obj, int index) {
        String name = "name_" + index;
        if (name.length() > 200) {
            name = name.substring(0, 200);
        }
        obj.setName(name);
    }
    
    public CmsLayout CmsLayoutDataOnDemand.getSpecificCmsLayout(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        CmsLayout obj = data.get(index);
        Integer id = obj.getId();
        return CmsLayout.findCmsLayout(id);
    }
    
    public CmsLayout CmsLayoutDataOnDemand.getRandomCmsLayout() {
        init();
        CmsLayout obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return CmsLayout.findCmsLayout(id);
    }
    
    public boolean CmsLayoutDataOnDemand.modifyCmsLayout(CmsLayout obj) {
        return false;
    }
    
    public void CmsLayoutDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = CmsLayout.findCmsLayoutEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'CmsLayout' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<CmsLayout>();
        for (int i = 0; i < 10; i++) {
            CmsLayout obj = getNewTransientCmsLayout(i);
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