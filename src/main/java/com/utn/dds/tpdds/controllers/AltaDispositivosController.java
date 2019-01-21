package com.utn.dds.tpdds.controllers;

import com.utn.dds.tpdds.repository.CatalogoDispositivosJpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class AltaDispositivosController {
    @RequestMapping(value = "/submitAlta", method = RequestMethod.POST)
    public String submit(BindingResult result, Model model, @RequestParam("dispositivo") String nombreDispositivo, @RequestParam("kwPorHora") BigDecimal kwPorHora, @RequestParam(value = "Inteligente", required = false) String inteligente, @RequestParam(value = "Bajo consumo", required = false) String bajoConsumo) {
        //List dispositivosCatalogo = CatalogoDispositivosJpaRepository.buscarDispositivoDeCatalogoPorNombre(nombreDispositivo);
        return "";
    }
}
