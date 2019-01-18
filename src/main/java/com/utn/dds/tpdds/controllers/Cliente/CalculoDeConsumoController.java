package com.utn.dds.tpdds.controllers.Cliente;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/cliente/calculoDeConsumo")
public class CalculoDeConsumoController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String calculoDeConsumo() {
        return "calculoDeConsumo";
    }
}
