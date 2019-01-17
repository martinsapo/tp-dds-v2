package com.utn.dds.tpdds.controllers.Cliente;

import com.utn.model.*;
import com.utn.model.DTOs.ReglaDTO;
import com.utn.repositorio.RepositorioDeClientesResidenciales;
import com.utn.repositorio.RepositorioDeDispositivosInteligentes;
import com.utn.repositorio.RepositorioDeReglas;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
public class ReglasABMController {
    @RequestMapping(value = "/reglasABM", method = RequestMethod.GET)
    public ModelAndView reglasABM(HttpServletRequest request) {
        Integer clienteId = ((ClienteResidencial) request.getSession().getAttribute("cliente")).getId();
        ClienteResidencial clienteResidencial =  RepositorioDeClientesResidenciales.buscarPorId(clienteId);
        List<ReglaDTO> reglas = clienteResidencial.obtenerReglasDeTodosLosDispositivos();

        ModelMap model = new ModelMap();
        model.addAttribute("reglas", reglas);
        return new ModelAndView("reglasABM", model);
    }

    @RequestMapping(value = "/altaRegla", method = RequestMethod.GET)
    public ModelAndView altaRegla(HttpServletRequest request) {
        Integer clienteId = ((ClienteResidencial) request.getSession().getAttribute("cliente")).getId();
        ClienteResidencial clienteResidencial =  RepositorioDeClientesResidenciales.buscarPorId(clienteId);
        List<DispositivoInteligente> dispositivoInteligentes = clienteResidencial.getDispositivosInteligentes();


        ModelMap model = new ModelMap();

        model.addAttribute("accionesPosibles", Arrays.asList(AccionesPosibles.values()));
        model.addAttribute("dispositivosInteligentes", dispositivoInteligentes);
        model.addAttribute("operadores", Arrays.asList(Operador.values()));
        return new ModelAndView("reglasAlta", model);
    }

    @RequestMapping(value = "/guardarRegla", method = RequestMethod.GET)
    public ModelAndView guardarRegla(HttpServletRequest request, @RequestParam("accion") String accionParam, @RequestParam("dispositivo") String dispositivoId, @RequestParam("tipoCondicion") String tipoCondicion, @RequestParam("operador") String operador, @RequestParam("value") String valor) {
        Condicion condicion;

        if (tipoCondicion == "valor") {
            condicion = new CondicionPorValor(new Double(valor), Operador.valueOf(operador));
        } else if (tipoCondicion == "Binaria") {
            condicion = new CondicionBinaria(Boolean.valueOf(valor), Operador.valueOf(operador));
        } else {
            condicion = new CondicionDeConsumoMensual(new Double(valor), Operador.valueOf(operador));
        }
        Accion accion = new Accion(AccionesPosibles.valueOf(accionParam));

        DispositivoInteligente dispositivoInteligente = RepositorioDeDispositivosInteligentes.buscarPorId(Integer.parseInt(dispositivoId));
        Regla regla = new Regla(condicion, accion, dispositivoInteligente, dispositivoInteligente.getSensores().get(0));
        RepositorioDeReglas.persistir(regla);

        return new ModelAndView("redirect:/reglasABM");
    }

    @RequestMapping(value = "/eliminarRegla", method = RequestMethod.GET)
    public ModelAndView eliminarRegla(HttpServletRequest request, @RequestParam("id") String id) {
        Regla regla = RepositorioDeReglas.buscarPorId(new Integer(id));
        RepositorioDeReglas.eliminar(regla);
        return new ModelAndView("redirect:/reglasABM");
    }

}
