package com.utn.dds.tpdds.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
public class Categoria implements Serializable{
    public Double getCargoFijo() {
        return cargoFijo;
    }

    public void setCargoFijo(Double cargoFijo) {
        this.cargoFijo = cargoFijo;
    }

    private Double cargoFijo;

    public Double getCargoVariable() {
        return cargoVariable;
    }

    public void setCargoVariable(Double cargoVariable) {
        this.cargoVariable = cargoVariable;
    }

    private Double cargoVariable;

    public Categoria(Double consumoMensual) {
        ActualizarConsumo(consumoMensual);
    }

    public Categoria() {

    }

    public Double GetMontoMensual(Double consumoMensual) {
        return cargoFijo + cargoVariable * consumoMensual;
    }

    public void ActualizarConsumo(Double consumoMensual) {
        if(consumoMensual <= 150){
            cargoFijo = 18.76;
            cargoVariable = 0.644;
            setNCategoria("0");
        } else if(consumoMensual <= 325) {
            cargoFijo = 35.32;
            cargoVariable = 0.644;
            setNCategoria("1");
        } else if(consumoMensual <= 400){
            cargoFijo = 60.71;
            cargoVariable = 0.681;
            setNCategoria("2");
        } else if(consumoMensual <= 450){
            cargoFijo = 71.74;
            cargoVariable = 0.738;
            setNCategoria("3");
        } else if(consumoMensual <= 500){
            cargoFijo = 110.38;
            cargoVariable = 0.794;
            setNCategoria("4");
        } else if(consumoMensual <= 600){
            cargoFijo = 220.75;
            cargoVariable = 0.832;
            setNCategoria("5");
        } else if(consumoMensual <= 700){
            cargoFijo = 443.59;
            cargoVariable = 0.851;
            setNCategoria("6");
        } else if(consumoMensual <= 1400){
            cargoFijo = 545.96;
            cargoVariable = 0.851;
            setNCategoria("7");
        } else {
            cargoFijo = 887.19;
            cargoVariable = 0.851;
            setNCategoria("8");
        }
    }
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nCategoria;
    @OneToOne(mappedBy = "categoria")
    private ClienteResidencial cliente;

    public String getNCategoria() {
        return nCategoria;
    }

    public void setNCategoria(String nCategoria) {
        this.nCategoria = nCategoria;
    }
}
