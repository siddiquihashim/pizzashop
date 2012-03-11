package com.springsource.roo.pizzashop.web;

import com.springsource.roo.pizzashop.domain.Base;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "bases", formBackingObject = Base.class)
@RequestMapping("/bases")
@Controller
public class BaseController {
}
