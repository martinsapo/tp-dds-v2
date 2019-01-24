package com.utn.dds.tpdds.controllers.Cliente;

import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.model.DispositivoEstandar;
import com.utn.dds.tpdds.model.DispositivoInteligente;
import com.utn.dds.tpdds.repository.CatalogoDispositivosJpaRepository;
import com.utn.dds.tpdds.repository.ClienteResidencialJpaRepository;
import com.utn.dds.tpdds.repository.DispositivoEstandarJpaRepository;
import com.utn.dds.tpdds.repository.DispositivoInteligenteJpaRepository;
import com.utn.dds.tpdds.repository.DispositivoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "cliente/abmDispositivos")
public class DispositivosABMController {

    @Autowired ClienteResidencialJpaRepository clienteResidencialJPARepository;
    @Autowired DispositivoInteligenteJpaRepository dispositivoInteligenteJpaRepository;
    @Autowired DispositivoEstandarJpaRepository dispositivoEstandarJpaRepository;
    @Autowired DispositivoJpaRepository dispositivoJpaRepository;
    @Autowired CatalogoDispositivosJpaRepository catalogoDispositivosJpaRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView submitDispositivoABM(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        ClienteResidencial cliente = ((ClienteResidencial) request.getSession().getAttribute("cliente"));
        List<DispositivoInteligente> dispositivosInteligentesDelCliente = dispositivoInteligenteJpaRepository.findDispositivoInteligentesByDueno(cliente);
        List<DispositivoEstandar> dispositivosEstandarDelCliente = dispositivoEstandarJpaRepository.findDispositivoEstandarByDueno(cliente);

        model.addAttribute("dispositivosInteligentes", dispositivosInteligentesDelCliente);
        model.addAttribute("dispositivosEstandar", dispositivosEstandarDelCliente);
        return new ModelAndView("dispositivosABM", model);
    }

    @RequestMapping(value = "/alta", method = RequestMethod.GET)
    public ModelAndView alta(HttpServletRequest request) {
        ClienteResidencial cliente = ((ClienteResidencial) request.getSession().getAttribute("cliente"));
        List<DispositivoInteligente> dispositivoInteligentes = cliente.getDispositivosInteligentes();
        ModelMap model = new ModelMap();
        //cargar datos para el alta
        return new ModelAndView("dispositivosAlta", model);
    }

    @RequestMapping(value = "/alta/submit", method = RequestMethod.GET)
    public ModelAndView altaSubmit() {

        //guardar dispositivo
        return new ModelAndView("redirect:/cliente/abmDispositivos");
    }

    @RequestMapping(value = "/baja/submit", method = RequestMethod.GET)
    public ModelAndView bajaSubmit(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        dispositivoJpaRepository.deleteById(id);
        return new ModelAndView("redirect:/cliente/abmDispositivos");
    }
}