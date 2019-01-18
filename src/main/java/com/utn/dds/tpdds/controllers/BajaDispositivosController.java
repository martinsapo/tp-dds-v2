package com.utn.dds.tpdds.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BajaDispositivosController {
    /*@RequestMapping(value = "/bajaDispositivo", method = RequestMethod.GET)
    public ModelAndView submitBaja(@ModelAttribute("userFormData") LoginDTO formData, Model model, HttpServletRequest request) {

        String username = request.getSession().getAttribute("username").toString();
        List<CatalogoDispositivos> dispositivosDelCatalogo =  RepositorioDeDispositivosDeCatalogo.obtenerDispositivosDeCatalogo();

        //List<Administrador> admins = (List<Administrador>)RepositorioDeAdministradores.getAll();
//        List dispositivoList = RepositorioDeDispositivosInteligentes.buscarPorClienteId(cliente.id);
//        dispositivoList.addAll(RepositorioDeDispositivosEstandar.buscarPorClienteId(cliente.id));

        model.addAttribute("dispositivosDelCatalogo", dispositivosDelCatalogo);
        model.addAttribute("username",username);
        return new ModelAndView("bajaDispositivo", null);
    }

    @RequestMapping(value = "/submitBajaDispositivos", method = RequestMethod.GET)
    public ModelAndView submitBajaDispositivos(@ModelAttribute("userFormData") LoginDTO formData, Model model, HttpServletRequest request) {

        return new ModelAndView("redirect:/submitBaja");
    }*/

}
