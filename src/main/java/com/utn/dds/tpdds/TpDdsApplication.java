package com.utn.dds.tpdds;

import com.utn.dds.tpdds.model.Hogar;
import com.utn.dds.tpdds.model.ZonaGeografica;
import com.utn.dds.tpdds.repository.CatalogoDispositivosJpaRepository;
import com.utn.dds.tpdds.repository.ZonaGeograficaJpaRepository;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TpDdsApplication {

    @Autowired ZonaGeograficaJpaRepository zonaGeograficaJpaRepository;

	public static void main(String[] args) {
		SpringApplication.run(TpDdsApplication.class, args);
	}

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        List<ZonaGeografica> zonaGeograficaList = zonaGeograficaJpaRepository.findAll();
        List<Hogar> hogares = new ArrayList<>();
        for(ZonaGeografica zonaGeografica : zonaGeograficaList) {
            hogares.addAll(zonaGeografica.getAllHogares());
        }

        for (Hogar hogar : hogares) {
            if (hogar.getCliente().getAhorroAutomatico()) {
                AbstractApplicationContext factory = new ClassPathXmlApplicationContext("spring-quartz.xml");
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
                    //corre cada 2 horas
                    trigger.setRepeatInterval(1000*60*60*2);
                    trigger.afterPropertiesSet();

                    scheduler.scheduleJob(jobDetail.getObject(), trigger.getObject());

                    // Start Scheduler
                    scheduler.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}

