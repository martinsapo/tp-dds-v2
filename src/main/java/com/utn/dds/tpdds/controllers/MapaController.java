package com.utn.dds.tpdds.controllers;

import com.utn.dds.tpdds.model.Transformador;
import com.utn.dds.tpdds.repository.TransformadorJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/mapa")
public class MapaController {
    @Autowired TransformadorJpaRepository transformadorJpaRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView cargarMapa() {
        List<Transformador> transformadores = transformadorJpaRepository.findAll();
        for (Transformador transformador : transformadores) {
            transformador.calcularConsumoPorHoraDeTodosLosDispositivos();
        }
        ModelMap model = new ModelMap();
        model.addAttribute("list", transformadores);
        return new ModelAndView("mapa", model);
    }
}
