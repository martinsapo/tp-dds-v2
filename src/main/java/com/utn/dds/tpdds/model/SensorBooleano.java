package com.utn.dds.tpdds.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Random;

@Entity
@NoArgsConstructor
public class SensorBooleano extends Sensor {

    @Override public Object medir() {
        MedicionBooleana medicionBooleana = new MedicionBooleana(new Random().nextBoolean(), this);
        agregarMedicion(medicionBooleana);
        return medicionBooleana;
    }

    public SensorBooleano(DispositivoInteligente dispositivoInteligente, String nombre) {
        super(dispositivoInteligente, nombre);
    }
}
