package com.utn.dds.tpdds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class CondicionBinaria extends Condicion{

    private Boolean medicionEsperada;

    public CondicionBinaria(Boolean medicionEsperada, Operador operator) {
        super(operator);
        this.medicionEsperada = medicionEsperada;
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
