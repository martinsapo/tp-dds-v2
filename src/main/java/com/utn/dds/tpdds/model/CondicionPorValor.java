package com.utn.dds.tpdds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CondicionPorValor extends Condicion{

    private Double medicionEsperada;

    public CondicionPorValor(Double medicionEsperada, Operador operator) {
        super(operator);
        this.medicionEsperada = medicionEsperada;
    }

    @Override Boolean applies(DispositivoInteligente dispositivoInteligente, Sensor sensor) {
        if (sensor != null && sensor instanceof SensorDeValores) {
            return operator.apply(medicionEsperada, ((MedicionPorValor)sensor.medir()).getMedicion());
        }
        return false;
    }

    @Override public Object getMedicion() {
        return medicionEsperada;
    }
}
