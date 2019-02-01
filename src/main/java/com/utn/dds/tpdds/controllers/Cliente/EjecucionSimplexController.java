package com.utn.dds.tpdds.controllers.Cliente;

import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.model.Hogar;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView submitsimplex(HttpServletRequest request) {
        ModelMap model = new ModelMap();

        String jsonTablaDeDispositivos = "/Users/msaposnic/Documents/tp-dds/src/test/java/json";
        ClienteResidencial cliente = ((ClienteResidencial) request.getSession().getAttribute("cliente"));
        cliente.getHogar().getTransformador().agregarTablaDeDispositivos(jsonTablaDeDispositivos);
        cliente.getHogar().obtenerRecomendacion();

        model.addAttribute("dispositivos", cliente.getDispositivos());
        model.addAttribute("hogar", cliente.getHogar());

        if (cliente.getAhorroAutomatico()){
            disparaEjecucionAutomaticaDelSimplex(cliente.getHogar());
        }

        return new ModelAndView("ejecutarSimplex", model);
    }

    private void disparaEjecucionAutomaticaDelSimplex(Hogar hogar){

        AbstractApplicationContext factory = new ClassPathXmlApplicationContext("spring-quartz.xml");


        //get the quartzFactory bean
        Scheduler scheduler = (Scheduler) factory.getBean("scheduler");

        try {
            // create JOB
            MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
            jobDetail.setTargetObject(hogar);
            jobDetail.setTargetMethod("ejecucionAutomatica");
            jobDetail.setName("MyJobDetail");
            jobDetail.setConcurrent(false);
            jobDetail.afterPropertiesSet();

            SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
            trigger.setBeanName("MyTrigger");
            trigger.setJobDetail(jobDetail.getObject());
            trigger.setRepeatInterval(5000);
            trigger.afterPropertiesSet();

            scheduler.scheduleJob(jobDetail.getObject(), trigger.getObject());

            // Start Scheduler
            scheduler.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
