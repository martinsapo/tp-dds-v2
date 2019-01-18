package com.utn.dds.tpdds.controllers.Cliente;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/simplex")
public class EjecucionSimplexController {

    @RequestMapping(value = "")
    public String simplex() {
        return "ejecutarSimplex";
    }

    /*@RequestMapping(value = "/submitsimplex", method = RequestMethod.GET)
    public String submitsimplex(@ModelAttribute("userFormData") LoginDTO formData, BindingResult result, Model model, HttpServletRequest request) {
        String jsonTablaDeDispositivos = "/home/eric/Desktop/DDS/tp-dds/archivos/json/tablaDispositivos.json";
        String username = request.getSession().getAttribute("username").toString();
        ClienteResidencial cliente = RepositorioDeClientesResidenciales.buscarPorUsername(username);
        cliente.getHogar().getTransformador().agregarTablaDeDispositivos(jsonTablaDeDispositivos);
        cliente.getHogar().obtenerRecomendacion();

        model.addAttribute("dispositivos", cliente.getDispositivos());
        model.addAttribute("hogar", cliente.getHogar());
        model.addAttribute("username",username);

        if (cliente.getAhorroAutomatico()){
            disparaEjecucionAutomaticaDelSimplex(cliente.getHogar());
        }


        return "EjecutarSimplex";
    }

    public void disparaEjecucionAutomaticaDelSimplex(Hogar hogar){

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
            trigger.setJobDetail((JobDetail) jobDetail.getObject());
            trigger.setRepeatInterval(5000);
            trigger.afterPropertiesSet();

            scheduler.scheduleJob((JobDetail) jobDetail.getObject(), trigger.getObject());

            // Start Scheduler
            scheduler.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
    }*/
}
