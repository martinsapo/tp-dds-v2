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
}