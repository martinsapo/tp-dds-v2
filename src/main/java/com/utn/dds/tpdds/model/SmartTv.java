package com.utn.dds.tpdds.model;


import javax.persistence.Entity;

/**
 * Created by msaposnic on Jun, 2018
 */
@Entity
public class SmartTv extends Driver {

    @Override
    void encender() {
        dispositivoInteligente.encender();
    }

    @Override
    void apagar() {
        dispositivoInteligente.apagar();
    }

    @Override
    void cambiarAModoAhorroDeEnergia() {
        dispositivoInteligente.cambiarAAhorroDeEnergia();
    }

    @Override
    void subirIntensidad() {

    }

    @Override
    void bajarIntensidad() {

    }
}
