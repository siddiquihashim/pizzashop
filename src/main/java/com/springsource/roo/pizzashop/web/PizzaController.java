package com.springsource.roo.pizzashop.web;

import com.springsource.roo.pizzashop.domain.Pizza;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "pizzas", formBackingObject = Pizza.class)
@RequestMapping("/pizzas")
@Controller
public class PizzaController {
}
