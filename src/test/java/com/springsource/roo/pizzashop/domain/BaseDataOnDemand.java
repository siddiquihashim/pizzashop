package com.springsource.roo.pizzashop.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

@Component
@Configurable
@RooDataOnDemand(entity = Base.class)
public class BaseDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Base> data;

	public Base getNewTransientBase(int index) {
        Base obj = new Base();
        setName(obj, index);
        return obj;
    }

	public void setName(Base obj, int index) {
        String name = "name_" + index;
        obj.setName(name);
    }

	public Base getSpecificBase(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Base obj = data.get(index);
        return Base.findBase(obj.getId());
    }

	public Base getRandomBase() {
        init();
        Base obj = data.get(rnd.nextInt(data.size()));
        return Base.findBase(obj.getId());
    }

	public boolean modifyBase(Base obj) {
        return false;
    }

	public void init() {
        data = Base.findBaseEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Base' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<com.springsource.roo.pizzashop.domain.Base>();
        for (int i = 0; i < 10; i++) {
            Base obj = getNewTransientBase(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> it = e.getConstraintViolations().iterator(); it.hasNext();) {
                    ConstraintViolation<?> cv = it.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
}
