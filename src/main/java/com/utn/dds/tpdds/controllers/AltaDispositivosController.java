package com.utn.dds.tpdds.controllers;

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
    /*@RequestMapping(value = "/submitAlta", method = RequestMethod.POST)
    public String submit(@ModelAttribute("userFormData") CreacionDispositivoDTO formData, BindingResult result, Model model, @RequestParam("dispositivo") String nombreDispositivo,
                         @RequestParam("kwPorHora") BigDecimal kwPorHora, @RequestParam(value = "Inteligente", required = false) String inteligente,
                         @RequestParam(value = "Bajo consumo", required = false) String bajoConsumo) {


        //  List dispositivosEstandar = RepositorioDeDispositivosEstandar.buscarDispositivoEstandarPorNombre(nombreDispositivo);


        List dispositivosCatalogo = RepositorioDeDispositivosDeCatalogo.buscarDispositivoDeCatalogoPorNombre(nombreDispositivo);

/*
        if (inteligente != null) {
            CatalogoDispositivos dispositivoInteligente = new CatalogoDispositivos(nombreDispositivo, true, null, kwPorHora);

            if (dispositivosCatalogo.isEmpty()) {

                if (bajoConsumo != null) {
                }

                RepositorioDeDispositivosDeCatalogo.persistir(dispositivoInteligente);
                model.addAttribute("msg", "Dispositivo Creado");
                return "altaDispositivo";
            } else {
                model.addAttribute("msg", "El dispositivo ya existe");
                return "altaDispositivo";

            }
        } else if (inteligente == null) {
            CatalogoDispositivos dispositivoEstandar = new CatalogoDispositivos(nombreDispositivo, true, null, kwPorHora);

            if (dispositivosCatalogo.isEmpty()) {

                if (bajoConsumo != null) {
                }
                RepositorioDeDispositivosDeCatalogo.persistir(dispositivoEstandar);
                model.addAttribute("msg", "Dispositivo Creado");
                return "altaDispositivo";


            } else {
                model.addAttribute("msg", "El dispositivo ya existe");
                return "altaDispositivo";

            }
*/

    /*CatalogoDispositivos dispositivo = new CatalogoDispositivos(nombreDispositivo, null, null, kwPorHora);

    if (dispositivosCatalogo.isEmpty()) {

        if (inteligente != null) {

            dispositivo.setEsInteligente(true);

        }else dispositivo.setEsInteligente(false);

        if (bajoConsumo != null) {
            dispositivo.setEsBajoConsumo(true);
        }else dispositivo.setEsBajoConsumo(false);

        RepositorioDeDispositivosDeCatalogo.persistir(dispositivo);
        model.addAttribute("msg", "Dispositivo Creado");
        return "altaDispositivo";
    } else {
        model.addAttribute("msg", "El dispositivo ya existe");
        return "altaDispositivo";

    }

    }*/
}
