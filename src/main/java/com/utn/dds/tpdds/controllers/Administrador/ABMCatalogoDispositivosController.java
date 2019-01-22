package com.utn.dds.tpdds.controllers.Administrador;

import com.utn.dds.tpdds.model.ItemDeCatalogoDeDispositivos;
import com.utn.dds.tpdds.repository.CatalogoDispositivosJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller
@RequestMapping(value = "/admin/abmCatalogoDispositivos")
public class ABMCatalogoDispositivosController {

    @Autowired CatalogoDispositivosJpaRepository catalogoDispositivosJpaRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelMap model = new ModelMap();
        model.addAttribute("catalogo", catalogoDispositivosJpaRepository.findAll());
        return new ModelAndView("abmCatalogoDispositivos", model);
    }

    @RequestMapping(value = "/alta", method = RequestMethod.GET)
    public String alta() {
       return "altaDeItemDeCatalogoDeDispositivos";
    }

    @RequestMapping(value = "/alta/submit", method = RequestMethod.GET)
    public ModelAndView altaSubmit(HttpServletRequest request) {

        String nombre = request.getParameter("nombre");
        BigDecimal consumo = BigDecimal.valueOf(Double.parseDouble(request.getParameter("kwPorHora")));
        Boolean esInteligente = true;
        Boolean esBajoConsumo = true;
        if (request.getParameter("inteligente") == null) {
            esInteligente = false;
        }
        if (request.getParameter("bajoconsumo") == null) {
            esBajoConsumo = false;
        }

        ItemDeCatalogoDeDispositivos item = new ItemDeCatalogoDeDispositivos(nombre, esInteligente, esBajoConsumo, consumo);
        catalogoDispositivosJpaRepository.save(item);
        return new ModelAndView("redirect:/admin/abmCatalogoDispositivos");
    }

    @RequestMapping(value = "/baja/submit", method = RequestMethod.GET)
    public ModelAndView bajaSubmit(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        catalogoDispositivosJpaRepository.deleteById(id);
        return new ModelAndView("redirect:/admin/abmCatalogoDispositivos");
    }
}
