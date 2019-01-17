package com.utn.dds.tpdds.model;

import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
public class DispositivoEstandar extends Dispositivo implements Serializable{

    private int horasDeUsoPorDia;

    private Boolean fueConvertidoAInteligente;
    public DispositivoEstandar(){

    }

    public DispositivoEstandar(String nombreDelDispositivo, BigDecimal kwPorHora, ClienteResidencial dueno, int horasDeUsoPorDia){
        super(nombreDelDispositivo, kwPorHora, dueno);
        fueConvertidoAInteligente=false;
        this.horasDeUsoPorDia=horasDeUsoPorDia;
    }

    DispositivoEstandar(DispositivoInteligente dispositivoInteligente) {
        super(dispositivoInteligente.nombreDelDispositivo, dispositivoInteligente.kwQueConsumePorHora, dispositivoInteligente.dueno);
        fueConvertidoAInteligente=true;
    }

    public  DispositivoEstandar(String nombre, BigDecimal kwPorHora){
//  Esta se utiliza unicamente para el Json de la tabla de dispositivos para el Simplex
        this.nombreDelDispositivo = nombre;
        this.kwQueConsumePorHora = kwPorHora;
        fueConvertidoAInteligente=false;
    }

    public void convertidoAInteligente(){
            this.fueConvertidoAInteligente=true;
    }
    public Integer getPuntosASumar() {
        return 0;
    }

    public BigDecimal cantidadDeEnergiaConsumidaEnUnPeriodo(LocalDateTime startTime, LocalDateTime endTime){
        BigDecimal energiaConsumida;
        Period intervaloDias = Period.between(startTime.toLocalDate(),endTime.toLocalDate());
        energiaConsumida = kwQueConsumePorHora.multiply(new BigDecimal(intervaloDias.getDays())).multiply(new BigDecimal(horasDeUsoPorDia));
        return energiaConsumida;

    }

}
