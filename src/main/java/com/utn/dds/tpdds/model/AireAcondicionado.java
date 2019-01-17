package com.utn.dds.tpdds.model;


import javax.persistence.Entity;

@Entity
public class AireAcondicionado extends Driver{

    public AireAcondicionado() {
    }

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


    public AireAcondicionado(DispositivoInteligente dispositivoInteligente) {
        this.dispositivoInteligente = dispositivoInteligente;
    }
}
