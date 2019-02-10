package com.utn.dds.tpdds.model;

import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
public class DispositivoEstandar extends Dispositivo implements Serializable{

    private Double horasDeUsoPorDia;

    private Boolean fueConvertidoAInteligente;
    public DispositivoEstandar(){

    }

    public DispositivoEstandar(String nombreDelDispositivo, ClienteResidencial dueno, Double horasDeUsoPorDia, ItemDeCatalogoDeDispositivos itemDeCatalogoDeDispositivos){
        super(nombreDelDispositivo, dueno, itemDeCatalogoDeDispositivos);
        fueConvertidoAInteligente=false;
        this.horasDeUsoPorDia=horasDeUsoPorDia;
    }

    DispositivoEstandar(DispositivoInteligente dispositivoInteligente) {
        super(dispositivoInteligente.nombreDelDispositivo, dispositivoInteligente.dueno, dispositivoInteligente.getItemDeCatalogoDeDispositivos());
        fueConvertidoAInteligente=true;
    }

    public  DispositivoEstandar(String nombre, BigDecimal kwPorHora){
//  Esta se utiliza unicamente para el Json de la tabla de dispositivos para el Simplex
        this.nombreDelDispositivo = nombre;
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
        energiaConsumida = getItemDeCatalogoDeDispositivos().getConsumo().multiply(new BigDecimal(intervaloDias.getDays())).multiply(new BigDecimal(horasDeUsoPorDia));
        return energiaConsumida;

    }

    public BigDecimal cantidadDeEnergiaConsumidaEnEsteMes() {
        return cantidadDeEnergiaConsumidaEnUnPeriodo(LocalDateTime.now().withDayOfMonth(1), LocalDateTime.now());
    }

}
