package com.utn.dds.tpdds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Regla implements Serializable {
    @Override public String toString() {
        return "Regla{" + "id=" + id + ", condicion=" + condicion + ", accion="
                + accion + ", dispositivoInteligente=" + dispositivoInteligente
                + ", sensor=" + sensor + '}';
    }

    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Condicion condicion;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Accion accion;

    public Regla(Condicion condicion, Accion accion, DispositivoInteligente dispositivoInteligente){
        this.condicion = condicion;
        this.accion = accion;
        this.dispositivoInteligente = dispositivoInteligente;
        dispositivoInteligente.agregarRegla(this);
        condicion.setRegla(this);
        accion.setRegla(this);
    }

    public Regla(Condicion condicion, Accion accion, DispositivoInteligente dispositivoInteligente, Sensor sensor){
        this.condicion = condicion;
        this.accion = accion;
        this.dispositivoInteligente = dispositivoInteligente;
        dispositivoInteligente.agregarRegla(this);
        condicion.setRegla(this);
        accion.setRegla(this);
        this.sensor = sensor;
    }

    private Boolean applies(DispositivoInteligente dispositivoInteligente) {
        return condicion.applies(dispositivoInteligente, sensor);
    }

    Accion devolverAccionSiAplica(DispositivoInteligente dispositivoInteligente) {
        if (this.applies(dispositivoInteligente) && dispositivoInteligente.getDueno().getAhorroAutomatico()) {
            return accion;
        }
        return new NoAction();
    }

    @ManyToOne
    private DispositivoInteligente dispositivoInteligente;

    @OneToOne
    private Sensor sensor;
}
