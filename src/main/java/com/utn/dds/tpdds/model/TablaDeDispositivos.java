package com.utn.dds.tpdds.model;

public class TablaDeDispositivos {
    private String nombre;
    private boolean esInteligente;
    private boolean esBajoConsumo;
    private double consumo;
    private double usoMinimo;
    private double usoMaximo;

    public TablaDeDispositivos(){};


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEsInteligente() {
        return esInteligente;
    }

    public void setEsInteligente(boolean esInteligente) {
        this.esInteligente = esInteligente;
    }

    public boolean isEsBajoConsumo() {
        return esBajoConsumo;
    }

    public void setEsBajoConsumo(boolean esBajoConsumo) {
        this.esBajoConsumo = esBajoConsumo;
    }

    public double getConsumo() {
        return consumo;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    public double getUsoMinimo() {
        return usoMinimo;
    }

    public void setUsoMinimo(double usoMinimo) {
        this.usoMinimo = usoMinimo;
    }

    public double getUsoMaximo() {
        return usoMaximo;
    }

    public void setUsoMaximo(double usoMaximo) {
        this.usoMaximo = usoMaximo;
    }
}
