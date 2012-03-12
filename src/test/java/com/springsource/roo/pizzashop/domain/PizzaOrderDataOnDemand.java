package com.springsource.roo.pizzashop.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
@RooDataOnDemand(entity = PizzaOrder.class)
public class PizzaOrderDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<PizzaOrder> data;

	public PizzaOrder getNewTransientPizzaOrder(int index) {
        PizzaOrder obj = new PizzaOrder();
        setAddress(obj, index);
        setDeliveryDate(obj, index);
        setName(obj, index);
        setTotal(obj, index);
        return obj;
    }

	public void setAddress(PizzaOrder obj, int index) {
        String address = "address_" + index;
        if (address.length() > 30) {
            address = address.substring(0, 30);
        }
        obj.setAddress(address);
    }

	public void setDeliveryDate(PizzaOrder obj, int index) {
        Date deliveryDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setDeliveryDate(deliveryDate);
    }

	public void setName(PizzaOrder obj, int index) {
        String name = "name_" + index;
        obj.setName(name);
    }

	public void setTotal(PizzaOrder obj, int index) {
        Float total = new Integer(index).floatValue();
        obj.setTotal(total);
    }

	public PizzaOrder getSpecificPizzaOrder(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        PizzaOrder obj = data.get(index);
        return PizzaOrder.findPizzaOrder(obj.getId());
    }

	public PizzaOrder getRandomPizzaOrder() {
        init();
        PizzaOrder obj = data.get(rnd.nextInt(data.size()));
        return PizzaOrder.findPizzaOrder(obj.getId());
    }

	public boolean modifyPizzaOrder(PizzaOrder obj) {
        return false;
    }

	public void init() {
        data = PizzaOrder.findPizzaOrderEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'PizzaOrder' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<com.springsource.roo.pizzashop.domain.PizzaOrder>();
        for (int i = 0; i < 10; i++) {
            PizzaOrder obj = getNewTransientPizzaOrder(i);
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
