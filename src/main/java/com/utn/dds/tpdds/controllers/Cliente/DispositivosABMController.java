package com.utn.dds.tpdds.controllers.Cliente;

import com.utn.dds.tpdds.model.ItemDeCatalogoDeDispositivos;
import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.model.Dispositivo;
import com.utn.dds.tpdds.model.DispositivoEstandar;
import com.utn.dds.tpdds.model.DispositivoInteligente;
import com.utn.dds.tpdds.repository.CatalogoDispositivosJpaRepository;
import com.utn.dds.tpdds.repository.ClienteResidencialJpaRepository;
import com.utn.dds.tpdds.repository.DispositivoEstandarJpaRepository;
import com.utn.dds.tpdds.repository.DispositivoInteligenteJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired CatalogoDispositivosJpaRepository catalogoDispositivosJpaRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView submitDispositivoABM() {
        ModelMap model = new ModelMap();

        List<ItemDeCatalogoDeDispositivos> dispositivosDelCatalogo = catalogoDispositivosJpaRepository.findAll();

        model.addAttribute("dispositivosDelCatalogo", dispositivosDelCatalogo);
        return new ModelAndView("dispositivosABM", model);
    }

    @RequestMapping(value = "/asociarDispositivo", method = RequestMethod.GET)
    public ModelAndView asociarDispositivo(HttpServletRequest request, @RequestParam("catalogo") String catalogoIdElegido) {
        ModelMap model = new ModelMap();

        ClienteResidencial cliente = ((ClienteResidencial) request.getSession().getAttribute("cliente"));
        List<Dispositivo> dispositivosDelCliente = cliente.getDispositivos();
        List<ItemDeCatalogoDeDispositivos> dispositivosDelCatalogo = catalogoDispositivosJpaRepository.findAll();


        Optional<ItemDeCatalogoDeDispositivos> catalogoElegido = catalogoDispositivosJpaRepository.findById(Integer.parseInt(catalogoIdElegido));

        for (Dispositivo dispositivo : dispositivosDelCliente){
            if(catalogoElegido.isPresent() && dispositivo.getNombreDelDispositivo().equals(catalogoElegido.get().getNombre())){
                model.addAttribute("errorMessage", "El item de catalogo seleccionado ya existe");
                model.addAttribute("dispositivosDelCatalogo", dispositivosDelCatalogo);
                return new ModelAndView("dispositivosABM", model);
            }
        }
        if(catalogoElegido.isPresent() && catalogoElegido.get().getEsInteligente()){
            DispositivoInteligente nuevoDispositivoInteligente = new DispositivoInteligente(catalogoElegido.get().getNombre(), catalogoElegido.get().getConsumo(), cliente);
            dispositivoInteligenteJpaRepository.save(nuevoDispositivoInteligente);
            model.addAttribute("errorMessage", "Dispositivo Inteligente Creado");

        }
        else if (catalogoElegido.isPresent() ){
            DispositivoEstandar nuevoDispositivoEstandar = new DispositivoEstandar(catalogoElegido.get().getNombre(), catalogoElegido.get().getConsumo(), cliente,2);
            dispositivoEstandarJpaRepository.save(nuevoDispositivoEstandar);
            model.addAttribute("errorMessage", "Dispositivo Estandar Creado");
        }
        model.addAttribute("dispositivosDelCatalogo", dispositivosDelCatalogo);
        return new ModelAndView("dispositivosABM", model);
    }
}