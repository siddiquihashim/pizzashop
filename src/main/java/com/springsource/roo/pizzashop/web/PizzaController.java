package com.springsource.roo.pizzashop.web;

import com.springsource.roo.pizzashop.domain.Base;
import com.springsource.roo.pizzashop.domain.Pizza;
import com.springsource.roo.pizzashop.domain.Topping;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RooWebScaffold(path = "pizzas", formBackingObject = Pizza.class)
@RequestMapping("/pizzas")
@Controller
public class PizzaController {

	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Pizza pizza, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("pizza", pizza);
            return "pizzas/create";
        }
        uiModel.asMap().clear();
        pizza.persist();
        return "redirect:/pizzas/" + encodeUrlPathSegment(pizza.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("pizza", new Pizza());
        return "pizzas/create";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("pizza", Pizza.findPizza(id));
        uiModel.addAttribute("itemId", id);
        return "pizzas/show";
    }

	@RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("pizzas", Pizza.findPizzaEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Pizza.countPizzas() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("pizzas", Pizza.findAllPizzas());
        }
        return "pizzas/list";
    }

	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Pizza pizza, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("pizza", pizza);
            return "pizzas/update";
        }
        uiModel.asMap().clear();
        pizza.merge();
        return "redirect:/pizzas/" + encodeUrlPathSegment(pizza.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("pizza", Pizza.findPizza(id));
        return "pizzas/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Pizza.findPizza(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/pizzas";
    }

	@ModelAttribute("bases")
    public Collection<Base> populateBases() {
        return Base.findAllBases();
    }

	@ModelAttribute("pizzas")
    public Collection<Pizza> populatePizzas() {
        return Pizza.findAllPizzas();
    }

	@ModelAttribute("toppings")
    public Collection<Topping> populateToppings() {
        return Topping.findAllToppings();
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        }
        catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
