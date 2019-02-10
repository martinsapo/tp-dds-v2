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
public class CondicionDeConsumoMensual extends Condicion{

    private Double medicionEsperada;

    public CondicionDeConsumoMensual(Double medicionEsperada, Operador operator) {
        super(operator);
        this.medicionEsperada = medicionEsperada;
    }

    @Override Boolean applies(DispositivoInteligente dispositivoInteligente, Sensor sensor) {
        if (dispositivoInteligente != null) {
            return operator.apply(new BigDecimal(medicionEsperada), dispositivoInteligente.cantidadDeEnergiaConsumidaEnEsteMes());
        }
        return false;
    }

    @Override public Object getMedicion() {
        return medicionEsperada;
    }
}
