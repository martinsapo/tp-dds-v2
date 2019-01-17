package com.utn.dds.tpdds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ZonaGeografica {

    @javax.persistence.Id @javax.persistence.GeneratedValue(strategy= GenerationType.IDENTITY) @org.springframework.data.annotation.Transient
    private Integer id;

    @org.springframework.data.annotation.Id @javax.persistence.Transient
    private String documentId;

    private Double latitud;
    private Double longitud;
    private Double radio;
    private String nombre;

    @OneToMany(mappedBy = "zona",cascade = CascadeType.ALL)
    private List<Transformador> listaDeTransformadores = new ArrayList<>();

    public ZonaGeografica(String nombre, Double latitud, Double longitud, Double radio) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.radio = radio;
        this.nombre = nombre;
    }

    public void agregarTransformador(Transformador transformador){
        this.listaDeTransformadores.add(transformador);
    }

    public BigDecimal calcularConsumo() {
        BigDecimal energia = new BigDecimal(0);
        for (Transformador transformador : this.listaDeTransformadores){
            energia = energia.add(transformador.calcularConsumoPorHoraDeTodosLosDispositivos());
        }
        return energia;
    }
}
