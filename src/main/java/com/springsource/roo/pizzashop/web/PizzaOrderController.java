package com.springsource.roo.pizzashop.web;

import com.springsource.roo.pizzashop.domain.Pizza;
import com.springsource.roo.pizzashop.domain.PizzaOrder;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
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

@RooWebScaffold(path = "pizzaorders", formBackingObject = PizzaOrder.class)
@RequestMapping("/pizzaorders")
@Controller
public class PizzaOrderController {

	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid PizzaOrder pizzaOrder, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("pizzaOrder", pizzaOrder);
            addDateTimeFormatPatterns(uiModel);
            return "pizzaorders/create";
        }
        uiModel.asMap().clear();
        pizzaOrder.persist();
        return "redirect:/pizzaorders/" + encodeUrlPathSegment(pizzaOrder.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("pizzaOrder", new PizzaOrder());
        addDateTimeFormatPatterns(uiModel);
        return "pizzaorders/create";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("pizzaorder", PizzaOrder.findPizzaOrder(id));
        uiModel.addAttribute("itemId", id);
        return "pizzaorders/show";
    }

	@RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("pizzaorders", PizzaOrder.findPizzaOrderEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) PizzaOrder.countPizzaOrders() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("pizzaorders", PizzaOrder.findAllPizzaOrders());
        }
        addDateTimeFormatPatterns(uiModel);
        return "pizzaorders/list";
    }

	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid PizzaOrder pizzaOrder, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("pizzaOrder", pizzaOrder);
            addDateTimeFormatPatterns(uiModel);
            return "pizzaorders/update";
        }
        uiModel.asMap().clear();
        pizzaOrder.merge();
        return "redirect:/pizzaorders/" + encodeUrlPathSegment(pizzaOrder.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("pizzaOrder", PizzaOrder.findPizzaOrder(id));
        addDateTimeFormatPatterns(uiModel);
        return "pizzaorders/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        PizzaOrder.findPizzaOrder(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/pizzaorders";
    }

	@ModelAttribute("pizzas")
    public Collection<Pizza> populatePizzas() {
        return Pizza.findAllPizzas();
    }

	@ModelAttribute("pizzaorders")
    public Collection<PizzaOrder> populatePizzaOrders() {
        return PizzaOrder.findAllPizzaOrders();
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("pizzaOrder_deliverydate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
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
