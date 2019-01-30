package com.utn.dds.tpdds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class MedicionPorValor extends Medicion{

    private Double medicion;
    MedicionPorValor(Double v, SensorDeValores sensor) {
        super(sensor);
        medicion = v;
    }

    @Override
    public Object getMedicion() {
        return medicion;
    }
}
