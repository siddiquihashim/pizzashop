package com.springsource.roo.pizzashop.web;

import com.springsource.roo.pizzashop.domain.PizzaOrder;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "pizzaorders", formBackingObject = PizzaOrder.class)
@RequestMapping("/pizzaorders")
@Controller
public class PizzaOrderController {
}
