package com.utn.dds.tpdds.controllers.Administrador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin/abmDispositivosAdmin")
public class AbmDispositivosAdminController {
    @RequestMapping(value = "/alta", method = RequestMethod.GET)
    public String alta() {
       return "altaDispositivo";
    }

    @RequestMapping(value = "/baja", method = RequestMethod.GET)
    public ModelAndView baja() {
        return "bajaDispositivo";
    }

    @RequestMapping(value = "/modificacion", method = RequestMethod.GET)
    public ModelAndView modificacion() {

    }
}
