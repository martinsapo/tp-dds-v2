package com.utn.dds.tpdds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Accion implements Serializable {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    public Accion(AccionesPosibles accionARealizar) {
        this.accionARealizar = accionARealizar;
    }

    @Column(columnDefinition = "enum('APAGAR','PRENDER','MODO_AHORRO', 'NADA')")
    @Enumerated(EnumType.STRING)
    private AccionesPosibles accionARealizar;


    void apply(Driver driver){
        accionARealizar.apply(driver);
    }

    @OneToOne(mappedBy = "accion", optional = false)
    private Regla regla;
}
