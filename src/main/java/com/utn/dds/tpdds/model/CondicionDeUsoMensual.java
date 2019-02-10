package com.utn.dds.tpdds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CondicionDeUsoMensual extends Condicion{
    private Integer medicionEsperada;

    public CondicionDeUsoMensual(Integer medicionEsperada, Operador operator) {
        super(operator);
        this.medicionEsperada = medicionEsperada;
    }

    @Override Boolean applies(DispositivoInteligente dispositivoInteligente, Sensor sensor) {
        if (dispositivoInteligente != null) {
            return operator.apply(dispositivoInteligente.cantidadDeHorasUsadoEnEsteMes().doubleValue(), Double.parseDouble(medicionEsperada.toString()) );
        }
        return false;
    }

    @Override
    public Object getMedicion() {
        return medicionEsperada;
    }
}
