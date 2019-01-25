package com.utn.dds.tpdds.controllers.Cliente;

import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.model.DispositivoEstandar;
import com.utn.dds.tpdds.model.DispositivoInteligente;
import com.utn.dds.tpdds.model.ItemDeCatalogoDeDispositivos;
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
import java.util.Optional;

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

    @RequestMapping(value = "/encender", method = RequestMethod.GET)
    public ModelAndView encender(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));

        Optional<DispositivoInteligente> optionalDispositivoInteligente = dispositivoInteligenteJpaRepository.findById(id);

        if (optionalDispositivoInteligente.isPresent()) {
            DispositivoInteligente dispositivoInteligente = optionalDispositivoInteligente.get();
            dispositivoInteligente.encender();
            dispositivoInteligenteJpaRepository.save(dispositivoInteligente);
        }

        return new ModelAndView("redirect:/cliente/abmDispositivos");
    }

    @RequestMapping(value = "/apagar", method = RequestMethod.GET)
    public ModelAndView apagar(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));

        Optional<DispositivoInteligente> optionalDispositivoInteligente = dispositivoInteligenteJpaRepository.findById(id);

        if (optionalDispositivoInteligente.isPresent()) {
            DispositivoInteligente dispositivoInteligente = optionalDispositivoInteligente.get();
            dispositivoInteligente.apagar();
            dispositivoInteligenteJpaRepository.save(dispositivoInteligente);
        }

        return new ModelAndView("redirect:/cliente/abmDispositivos");
    }

    @RequestMapping(value = "/modoAhorro", method = RequestMethod.GET)
    public ModelAndView modoAhorro(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));

        Optional<DispositivoInteligente> optionalDispositivoInteligente = dispositivoInteligenteJpaRepository.findById(id);

        if (optionalDispositivoInteligente.isPresent()) {
            DispositivoInteligente dispositivoInteligente = optionalDispositivoInteligente.get();
            dispositivoInteligente.cambiarAAhorroDeEnergia();
            dispositivoInteligenteJpaRepository.save(dispositivoInteligente);
        }

        return new ModelAndView("redirect:/cliente/abmDispositivos");
    }

    @RequestMapping(value = "/altaInteligente", method = RequestMethod.GET)
    public ModelAndView altaInteligente() {
        ModelMap model = new ModelMap();
        List<ItemDeCatalogoDeDispositivos> items = catalogoDispositivosJpaRepository.findAll();
        model.addAttribute("catalogo", items);
        return new ModelAndView("dispositivosInteligentesAlta", model);
    }

    @RequestMapping(value = "/altaEstandar", method = RequestMethod.GET)
    public ModelAndView altaEstandar() {
        ModelMap model = new ModelMap();
        List<ItemDeCatalogoDeDispositivos> items = catalogoDispositivosJpaRepository.findAll();
        model.addAttribute("catalogo", items);
        return new ModelAndView("dispositivosEstandarAlta", model);
    }

    @RequestMapping(value = "/altaInteligente/submit", method = RequestMethod.GET)
    public ModelAndView altaInteligenteSubmit(HttpServletRequest request) {
        ClienteResidencial cliente = ((ClienteResidencial) request.getSession().getAttribute("cliente"));
        Integer idCatalogo = Integer.parseInt(request.getParameter("catalogo"));
        String nombre = request.getParameter("nombre");
        ItemDeCatalogoDeDispositivos itemDeCatalogoDeDispositivos = catalogoDispositivosJpaRepository.findById(idCatalogo).get();
        DispositivoInteligente dispositivoInteligente = new DispositivoInteligente(nombre, cliente, itemDeCatalogoDeDispositivos);
        dispositivoInteligenteJpaRepository.save(dispositivoInteligente);
        return new ModelAndView("redirect:/cliente/abmDispositivos");
    }

    @RequestMapping(value = "/altaEstandar/submit", method = RequestMethod.GET)
    public ModelAndView altaEstandarSubmit(HttpServletRequest request) {
        ClienteResidencial cliente = ((ClienteResidencial) request.getSession().getAttribute("cliente"));
        Integer idCatalogo = Integer.parseInt(request.getParameter("catalogo"));
        String nombre = request.getParameter("nombre");
        Double usodiario = Double.parseDouble(request.getParameter("usoDiario"));
        ItemDeCatalogoDeDispositivos itemDeCatalogoDeDispositivos = catalogoDispositivosJpaRepository.findById(idCatalogo).get();
        DispositivoEstandar dispositivoEstandar = new DispositivoEstandar(
                nombre, cliente, usodiario,itemDeCatalogoDeDispositivos);
        dispositivoEstandarJpaRepository.save(dispositivoEstandar);
        return new ModelAndView("redirect:/cliente/abmDispositivos");
    }

    @RequestMapping(value = "/baja/submit", method = RequestMethod.GET)
    public ModelAndView bajaSubmit(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        dispositivoJpaRepository.deleteById(id);
        return new ModelAndView("redirect:/cliente/abmDispositivos");
    }
}