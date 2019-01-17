package com.utn.dds.tpdds.controllers.Cliente;

import com.utn.model.*;
import com.utn.model.DTOs.LoginDTO;
import com.utn.repositorio.RepositorioDeClientesResidenciales;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@SessionAttributes("cliente")
@Scope("session")
public class ClienteController {

    @RequestMapping(value = "/submithogar", method = RequestMethod.GET)
    public ModelAndView submithogar(@ModelAttribute("estadoHogar") LoginDTO formData, BindingResult result) {
        return new ModelAndView("redirect:/hogarState");
    }

/*    @RequestMapping(value = "/submitconsum", method = RequestMethod.POST)
    public String submitconsum(@ModelAttribute("consumo") LoginDTO formData, BindingResult result) {

        return "consum";
    }*/

    @RequestMapping(value = "/submitconsum", method = RequestMethod.POST)
    public String submitconsum(@ModelAttribute("consumo") LoginDTO formData, BindingResult result, Model model, HttpServletRequest request) {

        Integer clienteId = ((ClienteResidencial) request.getSession().getAttribute("cliente")).getId();
        ClienteResidencial clienteResidencial =  RepositorioDeClientesResidenciales.buscarPorId(clienteId);
        clienteResidencial.cargarRegistrosDeCambioDeEstadoEnDispositivos();

        String inicio = request.getParameter("fechaInicio") + " 00:00";
        String fin = request.getParameter("fechaFin") + " 23:59";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime fechaInicio = LocalDateTime.parse(inicio, formatter);
        LocalDateTime fechaFin = LocalDateTime.parse(fin, formatter);

        model.addAttribute("consumoPorPeriodo", clienteResidencial.calcularConsumoEnUnPeriodo(fechaInicio, fechaFin));
        return "cliente";
    }

    @RequestMapping(value = "/submitcarga", method = RequestMethod.POST)
    public String submitcarga(@ModelAttribute("cargaArchivo") LoginDTO formData, BindingResult result) {
        return "cargarArchivo";
    }

//    @RequestMapping(value = "/submitsimplex", method = RequestMethod.POST)
//    public String submitsimplex(@ModelAttribute("simplex") LoginDTO formData, BindingResult result) {
//        return "EjecutarSimplex";
//    }
    @RequestMapping(value = "/submitreglas", method = RequestMethod.POST)
    public ModelAndView submitreglas(@ModelAttribute("reglasYDispositvos") LoginDTO formData, BindingResult result) {
        return new ModelAndView("redirect:/reglasABM");
    }

    @RequestMapping(value = "/submitdispositivos", method = RequestMethod.POST)
    public ModelAndView submitdispositivos(@ModelAttribute("reglasYDispositvos") LoginDTO formData, BindingResult result) {
        return new ModelAndView("redirect:/dispositivosABM");
    }
}

