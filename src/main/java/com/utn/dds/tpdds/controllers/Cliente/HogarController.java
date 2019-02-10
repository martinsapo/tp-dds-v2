package com.utn.dds.tpdds.controllers.Cliente;

import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.repository.ClienteResidencialJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/cliente/estadoHogar")
public class HogarController {

    @Autowired ClienteResidencialJpaRepository clienteResidencialJPARepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView submithogar(HttpServletRequest request) {
        ClienteResidencial cliente = ((ClienteResidencial) request.getSession().getAttribute("cliente"));

        ModelAndView modelAndView = new ModelAndView("hogar");
        modelAndView.addObject("dispositivosInteligentes", cliente.getDispositivosInteligentes());
        modelAndView.addObject("dispositivosEstandares", cliente.getDispositivosEstandars());
        modelAndView.addObject("reglas", cliente.obtenerReglasDeTodosLosDispositivos());
        modelAndView.addObject("consumo", cliente.obtenerTodosLosConsumosDeTodosLosDispositivosEnEsteMes().toString());
        modelAndView.addObject("mediciones", cliente.obtenerTodasLasMedicionesDeTodosLosDispositivos());

        return modelAndView;
    }
}
