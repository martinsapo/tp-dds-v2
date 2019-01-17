package com.utn.dds.tpdds.controllers.Cliente;

import com.utn.dds.tpdds.model.CatalogoDispositivos;
import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.repository.ClienteResidencialJpaRepository;
import com.utn.model.*;
import com.utn.model.DTOs.LoginDTO;
import com.utn.repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class DispositivosABMController {

    @Autowired ClienteResidencialJpaRepository clienteResidencialJPARepository;


    List<CatalogoDispositivos> dispositivosDelCatalogo =  RepositorioDeDispositivosDeCatalogo.obtenerDispositivosDeCatalogo();

    @RequestMapping(value = "/dispositivosABM", method = RequestMethod.GET)
    public ModelAndView submitDispositivoABM(@ModelAttribute("userFormData") LoginDTO formData,  Model model, HttpServletRequest request) {

        String username = request.getSession().getAttribute("username").toString();

        model.addAttribute("dispositivosDelCatalogo", dispositivosDelCatalogo);
        model.addAttribute("username",username);
        return new ModelAndView("dispositivosABM", null);
    }

    @RequestMapping(value = "/asociarDispositivo", method = RequestMethod.GET)
    public String asociarDispositivo(HttpServletRequest request, @RequestParam("catalogo") String catalogoIdElegido,Model model) {

        String username = request.getSession().getAttribute("username").toString();
        ClienteResidencial cliente = c.buscarPorUsername(username);
        List<Dispositivo> dispositivosDelCliente = cliente.getDispositivos();

        CatalogoDispositivos catalogoElegido = RepositorioDeDispositivosDeCatalogo.buscarPorId(Integer.parseInt(catalogoIdElegido));

        for (Dispositivo dispositivo : dispositivosDelCliente){
            if(dispositivo.getNombreDelDispositivo().equals(catalogoElegido.getNombre())){
                model.addAttribute("msg", "El item de catalogo seleccionado ya existe");
                model.addAttribute("dispositivosDelCatalogo", dispositivosDelCatalogo);
                return "dispositivosABM";
            }
        }
        if(catalogoElegido.getEsInteligente()){
            DispositivoInteligente nuevoDispositivoInteligente = new DispositivoInteligente(catalogoElegido.getNombre(), catalogoElegido.getConsumo(), cliente);
            RepositorioDeDispositivosInteligentes.persistir(nuevoDispositivoInteligente);
            model.addAttribute("msg", "Dispositivo Inteligente Creado");

        }
        else{

            DispositivoEstandar nuevoDispositivoEstandar = new DispositivoEstandar(catalogoElegido.getNombre(), catalogoElegido.getConsumo(), cliente,2);
            RepositorioDeDispositivosEstandar.persistir(nuevoDispositivoEstandar);
            model.addAttribute("msg", "Dispositivo Estandar Creado");
        }

        return "dispositivosABM";
    }
}