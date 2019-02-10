package com.utn.dds.tpdds.controllers.Cliente;

import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.repository.ClienteResidencialJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes("cliente")
@Scope("session")
@RequestMapping(value = "/cliente")
public class ClienteController {
    @Autowired ClienteResidencialJpaRepository clienteResidencialJPARepository;

    @RequestMapping(value = "")
    public String clienteMenu() {
        return "cliente";
    }

    @RequestMapping(value = "/ahorroAutomatico/habilitar")
    public ModelAndView habilitarAhorroAutomatico(HttpServletRequest request) {
        ClienteResidencial cliente = ((ClienteResidencial) request.getSession().getAttribute("cliente"));
        cliente.setAhorroAutomatico(true);
        clienteResidencialJPARepository.save(cliente);
        request.getSession().setAttribute("cliente", cliente);
        return new ModelAndView("redirect:/cliente");
    }

    @RequestMapping(value = "/ahorroAutomatico/deshabilitar")
    public ModelAndView deshabilitarAhorroAutomatico(HttpServletRequest request) {
        ClienteResidencial cliente = ((ClienteResidencial) request.getSession().getAttribute("cliente"));
        cliente.setAhorroAutomatico(false);
        clienteResidencialJPARepository.save(cliente);
        request.getSession().setAttribute("cliente", cliente);
        return new ModelAndView("redirect:/cliente");
    }

}

