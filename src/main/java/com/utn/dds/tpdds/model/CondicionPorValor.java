package com.utn.dds.tpdds.model;

import javax.persistence.Entity;

@Entity
public class CondicionPorValor extends Condicion{

    Double medicionEsperada;

    public CondicionPorValor(Double medicionEsperada, Operador operator) {
        super(operator);
        this.medicionEsperada = medicionEsperada;
    }

    public CondicionPorValor() {
        super();
    }

    @Override Boolean applies(DispositivoInteligente dispositivoInteligente, Sensor sensor) {
        if (sensor != null && sensor instanceof SensorDeValores) {
            return operator.apply(medicionEsperada, sensor.medir());
        }
        return false;
    }

    @Override
    public Object getMedicion() {
        return medicionEsperada;
    }
}
