package com.utn.dds.tpdds.model;

public enum TipoDeDocumento {
    DNI("Documento Nacional de Identidad"),
    CI("Cedula de Identidad"),
    LI("Libreta de Enrolamiento"),
    LC("Libreta Civica");

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    TipoDeDocumento(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    TipoDeDocumento() {

    }

    public String getAbreviatura() {
        return abreviatura;
    }

    private String abreviatura;
}
