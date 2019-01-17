package com.utn.dds.tpdds.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
public class CondicionDeConsumoMensual extends Condicion{

    Double medicionEsperada;

    public CondicionDeConsumoMensual(Double medicionEsperada, Operador operator) {
        super(operator);
        this.medicionEsperada = medicionEsperada;
    }

    @Override Boolean applies(DispositivoInteligente dispositivoInteligente, Sensor sensor) {
        if (dispositivoInteligente != null) {
            return operator.apply(new BigDecimal(medicionEsperada), dispositivoInteligente.cantidadDeEnergiaConsumidaEnElUltimoMes());
        }
        return false;
    }

    @Override
    public Object getMedicion() {
        return medicionEsperada;
    }
}
