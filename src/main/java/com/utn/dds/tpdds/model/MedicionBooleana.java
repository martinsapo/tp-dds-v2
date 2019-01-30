package com.utn.dds.tpdds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class MedicionBooleana extends Medicion{

    private Boolean medicion;

    MedicionBooleana(Boolean b, Sensor sensor) {
        super(sensor);
        medicion = b;
    }

    @Override
    public Object getMedicion() {
        return medicion;
    }
}
