package com.utn.dds.tpdds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Component
@NoArgsConstructor
@Getter
@Setter
public class Transformador {

    @javax.persistence.Id @javax.persistence.GeneratedValue(strategy= GenerationType.IDENTITY) @org.springframework.data.annotation.Transient
    private Integer id;

    @org.springframework.data.annotation.Id @javax.persistence.Transient
    private String documentId;

    private Double latitud;
    private Double longitud;

    @ManyToOne(cascade = CascadeType.ALL)
    private ZonaGeografica zona;

    @OneToMany(mappedBy = "transformador", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Hogar> listaDeHogares = new ArrayList<>();

    @Transient
    private double consumo;

    public Transformador(ZonaGeografica zona, Double latitud, Double longitud) {
        this.zona = zona;
        this.latitud = latitud;
        this.longitud = longitud;
        zona.agregarTransformador(this);
    }

    void agregarHogar(Hogar hogar) {
        listaDeHogares.add(hogar);
    }

    public BigDecimal calcularConsumoPorHoraDeTodosLosDispositivos(){
        consumo = 0;
        for (Hogar hogar : this.listaDeHogares){
            consumo += hogar.obtenerConsumosDeDispositivosPorHora().doubleValue();
        }
        return new BigDecimal(consumo);
    }

    public BigDecimal calcularConsumoEnUnPeriodo(LocalDateTime startTime, LocalDateTime endTime){
        BigDecimal energia = new BigDecimal(0);
        for (Hogar hogar : listaDeHogares) {
            energia = energia.add(hogar.consumoTotalEnHogar(startTime, endTime));
        }
        return energia;
    }
}
