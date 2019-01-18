package com.utn.dds.tpdds.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "")
public class IndexController {
    @RequestMapping(value = "")
    public ModelAndView LoginCliente() {
        return new ModelAndView("redirect:/loginCliente");
    }
}
