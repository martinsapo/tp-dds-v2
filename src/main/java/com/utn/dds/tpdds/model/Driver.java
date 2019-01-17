package com.utn.dds.tpdds.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by msaposnic on Jun, 2018
 */
@Entity
public abstract class Driver {

    protected Driver() {
    }

    public void setDispositivoInteligente(DispositivoInteligente dispositivoInteligente) {
        this.dispositivoInteligente = dispositivoInteligente;
    }

    @OneToOne
    DispositivoInteligente dispositivoInteligente;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    abstract void encender();
    abstract void apagar();
    abstract void cambiarAModoAhorroDeEnergia();
    abstract void subirIntensidad();
    abstract void bajarIntensidad();
}
