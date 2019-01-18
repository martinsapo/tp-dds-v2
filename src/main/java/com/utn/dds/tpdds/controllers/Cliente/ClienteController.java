package com.utn.dds.tpdds.controllers.Cliente;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("cliente")
@Scope("session")
@RequestMapping(value = "/cliente")
public class ClienteController {

    @RequestMapping(value = "")
    public String clienteMenu() {
        return "cliente";
    }

}

