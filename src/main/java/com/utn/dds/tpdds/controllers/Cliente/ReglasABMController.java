package com.utn.dds.tpdds.controllers.Cliente;

import com.utn.dds.tpdds.model.Accion;
import com.utn.dds.tpdds.model.AccionesPosibles;
import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.model.Condicion;
import com.utn.dds.tpdds.model.CondicionBinaria;
import com.utn.dds.tpdds.model.CondicionDeConsumoMensual;
import com.utn.dds.tpdds.model.CondicionDeUsoMensual;
import com.utn.dds.tpdds.model.CondicionPorValor;
import com.utn.dds.tpdds.model.DispositivoInteligente;
import com.utn.dds.tpdds.model.Operador;
import com.utn.dds.tpdds.model.Regla;
import com.utn.dds.tpdds.repository.DispositivoInteligenteJpaRepository;
import com.utn.dds.tpdds.repository.ReglaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/cliente/abmReglas")
public class ReglasABMController {

    @Autowired ReglaRepository reglaRepository;
    @Autowired DispositivoInteligenteJpaRepository dispositivoInteligenteJpaRepository;

    @RequestMapping(value = "")
    public ModelAndView index(HttpServletRequest request) {
        ClienteResidencial cliente = ((ClienteResidencial) request.getSession().getAttribute("cliente"));
        ModelMap model = new ModelMap();
        model.addAttribute("reglas", reglaRepository.findAllReglasFromClienteResidencial(cliente));
        return new ModelAndView("reglasABM", model);
    }

    @RequestMapping(value = "/alta", method = RequestMethod.GET)
    public ModelAndView alta(HttpServletRequest request) {
        ClienteResidencial cliente = ((ClienteResidencial) request.getSession().getAttribute("cliente"));
        List<DispositivoInteligente> dispositivoInteligentes = cliente.getDispositivosInteligentes();
        ModelMap model = new ModelMap();

        model.addAttribute("accionesPosibles", Arrays.asList(AccionesPosibles.values()));
        model.addAttribute("dispositivosInteligentes", dispositivoInteligentes);
        model.addAttribute("operadores", Arrays.asList(Operador.values()));
        return new ModelAndView("reglasAlta", model);
    }

    @RequestMapping(value = "/alta/submit", method = RequestMethod.GET)
    public ModelAndView altaSubmit(@RequestParam("accion") String accionParam, @RequestParam("dispositivo") String dispositivoId, @RequestParam("tipoCondicion") String tipoCondicion, @RequestParam("operador") String operador, @RequestParam("value") String valor) {
        Condicion condicion = null;

        switch (tipoCondicion) {
        case "valor":
            condicion = new CondicionPorValor(new Double(valor),
                    Operador.valueOf(operador));
            break;
        case "binaria":
            condicion = new CondicionBinaria(Boolean.valueOf(valor),
                    Operador.valueOf(operador));
            break;
        case "consumo":
            condicion = new CondicionDeConsumoMensual(new Double(valor),
                    Operador.valueOf(operador));
            break;
        case "uso":
            condicion = new CondicionDeUsoMensual(new Integer(valor),
                    Operador.valueOf(operador));
            break;
        }
        Accion accion = new Accion(AccionesPosibles.valueOf(accionParam));

        Optional<DispositivoInteligente> dispositivoInteligente = dispositivoInteligenteJpaRepository.findById(Integer.parseInt(dispositivoId));
        if (dispositivoInteligente.isPresent()) {
            Regla regla = new Regla(condicion, accion, dispositivoInteligente.get(), dispositivoInteligente.get().getSensores().get(0));
            reglaRepository.save(regla);
        }
        return new ModelAndView("redirect:/cliente/abmReglas");
    }

    @RequestMapping(value = "/baja/submit", method = RequestMethod.GET)
    public ModelAndView bajaSubmit(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        reglaRepository.deleteById(id);
        return new ModelAndView("redirect:/cliente/abmReglas");
    }
}
