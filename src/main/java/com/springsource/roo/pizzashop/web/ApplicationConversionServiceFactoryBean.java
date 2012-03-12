package com.springsource.roo.pizzashop.web;

import com.springsource.roo.pizzashop.domain.Base;
import com.springsource.roo.pizzashop.domain.Pizza;
import com.springsource.roo.pizzashop.domain.PizzaOrder;
import com.springsource.roo.pizzashop.domain.Topping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.RooConversionService;

/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(new BaseConverter());
        registry.addConverter(new PizzaConverter());
        registry.addConverter(new PizzaOrderConverter());
        registry.addConverter(new ToppingConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }

	static class BaseConverter implements Converter<Base, String> {
        public String convert(Base base) {
            return new StringBuilder().append(base.getName()).toString();
        }
        
    }

	static class PizzaConverter implements Converter<Pizza, String> {
        public String convert(Pizza pizza) {
            return new StringBuilder().append(pizza.getName()).append(" ").append(pizza.getPrice()).toString();
        }
        
    }

	static class PizzaOrderConverter implements Converter<PizzaOrder, String> {
        public String convert(PizzaOrder pizzaOrder) {
            return new StringBuilder().append(pizzaOrder.getName()).append(" ").append(pizzaOrder.getAddress()).append(" ").append(pizzaOrder.getTotal()).append(" ").append(pizzaOrder.getDeliveryDate()).toString();
        }
        
    }

	static class ToppingConverter implements Converter<Topping, String> {
        public String convert(Topping topping) {
            return new StringBuilder().append(topping.getName()).toString();
        }
        
    }
}
