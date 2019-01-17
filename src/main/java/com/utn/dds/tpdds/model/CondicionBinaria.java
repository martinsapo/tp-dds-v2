package com.utn.dds.tpdds.model;

import javax.persistence.Entity;

@Entity
public class CondicionBinaria extends Condicion{

    private Boolean medicionEsperada;

    public CondicionBinaria(Boolean medicionEsperada, Operador operator) {
        super(operator);
        this.medicionEsperada = medicionEsperada;
    }

    public CondicionBinaria() {
        super();
    }

    @Override
    Boolean applies(DispositivoInteligente dispositivoInteligente, Sensor sensor) {
        if (sensor != null && sensor instanceof SensorBooleano) {
            return operator.apply(medicionEsperada, sensor.medir());
        }
        return false;
    }

    @Override public Object getMedicion() {
        return medicionEsperada;
    }

}
