// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.springsource.roo.pizzashop.web;

import com.springsource.roo.pizzashop.domain.Base;
import com.springsource.roo.pizzashop.domain.Pizza;
import com.springsource.roo.pizzashop.domain.PizzaOrder;
import com.springsource.roo.pizzashop.domain.Topping;
import java.lang.String;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(new BaseConverter());
        registry.addConverter(new PizzaConverter());
        registry.addConverter(new PizzaOrderConverter());
        registry.addConverter(new ToppingConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
    static class com.springsource.roo.pizzashop.web.ApplicationConversionServiceFactoryBean.BaseConverter implements Converter<Base, String> {
        public String convert(Base base) {
            return new StringBuilder().append(base.getName()).toString();
        }
        
    }
    
    static class com.springsource.roo.pizzashop.web.ApplicationConversionServiceFactoryBean.PizzaConverter implements Converter<Pizza, String> {
        public String convert(Pizza pizza) {
            return new StringBuilder().append(pizza.getName()).append(" ").append(pizza.getPrice()).toString();
        }
        
    }
    
    static class com.springsource.roo.pizzashop.web.ApplicationConversionServiceFactoryBean.PizzaOrderConverter implements Converter<PizzaOrder, String> {
        public String convert(PizzaOrder pizzaOrder) {
            return new StringBuilder().append(pizzaOrder.getName()).append(" ").append(pizzaOrder.getAddress()).append(" ").append(pizzaOrder.getTotal()).append(" ").append(pizzaOrder.getDeliveryDate()).toString();
        }
        
    }
    
    static class com.springsource.roo.pizzashop.web.ApplicationConversionServiceFactoryBean.ToppingConverter implements Converter<Topping, String> {
        public String convert(Topping topping) {
            return new StringBuilder().append(topping.getName()).toString();
        }
        
    }
    
}
