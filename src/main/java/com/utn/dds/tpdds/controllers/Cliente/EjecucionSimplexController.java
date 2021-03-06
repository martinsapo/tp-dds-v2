package com.utn.dds.tpdds.controllers.Cliente;

import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.model.Hogar;
import com.utn.dds.tpdds.repository.CatalogoDispositivosJpaRepository;
import com.utn.dds.tpdds.repository.ClienteResidencialJpaRepository;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/cliente/simplex")
public class EjecucionSimplexController {
    @Autowired
    CatalogoDispositivosJpaRepository catalogoDispositivosJpaRepository;
    @Autowired ClienteResidencialJpaRepository clienteResidencialJpaRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView submitsimplex(HttpServletRequest request) {
        ModelMap model = new ModelMap();

        ClienteResidencial cliente = ((ClienteResidencial) request.getSession().getAttribute("cliente"));
        cliente = clienteResidencialJpaRepository.findById(cliente.getId()).get();
        cliente.getHogar().obtenerRecomendacion();

        model.addAttribute("dispositivos", cliente.getDispositivos());
        model.addAttribute("hogar", cliente.getHogar());
        String mensajeDeEficiencia = "";
        if (cliente.obtenerTodosLosConsumosDeTodosLosDispositivosEnEsteMes().doubleValue() <= (612 * 24 * 30)) {
            mensajeDeEficiencia = "Su hogar es eficiente";
        } else{
            mensajeDeEficiencia = "Su hogar NO es eficiente, por favor use menos los dispositivos";
        }
        model.addAttribute("esEficiente", mensajeDeEficiencia);
        return new ModelAndView("ejecutarSimplex", model);
    }
}
