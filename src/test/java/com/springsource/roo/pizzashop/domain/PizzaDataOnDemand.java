package com.springsource.roo.pizzashop.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

@Component
@Configurable
@RooDataOnDemand(entity = Pizza.class)
public class PizzaDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Pizza> data;

	@Autowired
    private BaseDataOnDemand baseDataOnDemand;

	public Pizza getNewTransientPizza(int index) {
        Pizza obj = new Pizza();
        setBase(obj, index);
        setName(obj, index);
        setPrice(obj, index);
        return obj;
    }

	public void setBase(Pizza obj, int index) {
        Base base = baseDataOnDemand.getRandomBase();
        obj.setBase(base);
    }

	public void setName(Pizza obj, int index) {
        String name = "name_" + index;
        obj.setName(name);
    }

	public void setPrice(Pizza obj, int index) {
        Float price = new Integer(index).floatValue();
        obj.setPrice(price);
    }

	public Pizza getSpecificPizza(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Pizza obj = data.get(index);
        return Pizza.findPizza(obj.getId());
    }

	public Pizza getRandomPizza() {
        init();
        Pizza obj = data.get(rnd.nextInt(data.size()));
        return Pizza.findPizza(obj.getId());
    }

	public boolean modifyPizza(Pizza obj) {
        return false;
    }

	public void init() {
        data = Pizza.findPizzaEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Pizza' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<com.springsource.roo.pizzashop.domain.Pizza>();
        for (int i = 0; i < 10; i++) {
            Pizza obj = getNewTransientPizza(i);
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
