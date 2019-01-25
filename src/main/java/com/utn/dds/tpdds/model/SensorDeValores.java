package com.utn.dds.tpdds.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
public class SensorDeValores extends Sensor {

    @Override public Object medir() {
        MedicionPorValor medicionPorValor = new MedicionPorValor(Math.random() * 1000 + 1, this);
        agregarMedicion(medicionPorValor);
        return medicionPorValor;
    }

    public SensorDeValores(DispositivoInteligente dispositivoInteligente, String nombre) {
        super(dispositivoInteligente, nombre);
    }
}
