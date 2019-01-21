package com.utn.dds.tpdds.controllers.Cliente;

import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.repository.ClienteResidencialJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
@RequestMapping(value = "/cliente/calculoDeConsumo")
public class CalculoDeConsumoController {

    @Autowired ClienteResidencialJpaRepository clienteResidencialJPARepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "calculoDeConsumo";
    }

    @RequestMapping(value = "submit", method = RequestMethod.GET)
    public ModelAndView calcularConsumo(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        ClienteResidencial cliente = ((ClienteResidencial) request.getSession().getAttribute("cliente"));

        String inicio = request.getParameter("inicio") + " 00:00";
        String fin = request.getParameter("fin") + " 23:59";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime fechaInicio = LocalDateTime.parse(inicio, formatter);
        LocalDateTime fechaFin = LocalDateTime.parse(fin, formatter);

        model.addAttribute("consumo", cliente.calcularConsumoEnUnPeriodo(fechaInicio, fechaFin));
        model.addAttribute("inicio", inicio);
        model.addAttribute("fin", fin);
        return new ModelAndView("calculoDeConsumo", model);
    }
}
