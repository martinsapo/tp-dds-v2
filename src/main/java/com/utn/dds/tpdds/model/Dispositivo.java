package com.utn.dds.tpdds.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonDeserialize(as=Dispositivo.class)
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class Dispositivo implements Serializable {

	public Dispositivo() {

	}

    @Id @GeneratedValue(strategy = GenerationType.TABLE)
	public Integer id;

	String nombreDelDispositivo;
	BigDecimal kwQueConsumePorHora;
    @ManyToOne(cascade = CascadeType.ALL)
    private ItemDeCatalogoDeDispositivos itemDeCatalogoDeDispositivos;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	ClienteResidencial dueno;

	Dispositivo(String nombreDelDispositivo, BigDecimal kwQueConsumePorHora, ClienteResidencial dueno, ItemDeCatalogoDeDispositivos itemDeCatalogoDeDispositivos) {
		this.nombreDelDispositivo = nombreDelDispositivo;
		this.kwQueConsumePorHora = kwQueConsumePorHora;
		this.dueno = dueno;
        dueno.addDispositivo(this);
        this.itemDeCatalogoDeDispositivos = itemDeCatalogoDeDispositivos;
        itemDeCatalogoDeDispositivos.getDispositivosAsociados().add(this);
	}

	public abstract Integer getPuntosASumar();

	public abstract BigDecimal cantidadDeEnergiaConsumidaEnUnPeriodo(LocalDateTime startDate, LocalDateTime endDate);

}


