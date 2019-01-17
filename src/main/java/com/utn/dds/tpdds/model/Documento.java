package com.utn.dds.tpdds.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Documento {
    @javax.persistence.Id @javax.persistence.GeneratedValue(strategy= GenerationType.IDENTITY) @org.springframework.data.annotation.Transient
    private Integer id;

    @org.springframework.data.annotation.Id @javax.persistence.Transient
    private String documentId;

    private String numeroDeDocumento;
    private TipoDeDocumento tipoDeDocumento;
    @OneToOne(mappedBy = "documento")
    private ClienteResidencial cliente;

    public Documento(String numeroDeDocumento, TipoDeDocumento tipoDeDocumento) {
        this.numeroDeDocumento = numeroDeDocumento;
        this.tipoDeDocumento = tipoDeDocumento;
    }
}
