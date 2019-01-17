package com.utn.dds.tpdds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.math3.optim.PointValuePair;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Hogar {
    @javax.persistence.Id @javax.persistence.GeneratedValue(strategy= GenerationType.IDENTITY) @org.springframework.data.annotation.Transient
    private Integer id;

    @org.springframework.data.annotation.Id @javax.persistence.Transient
    private String documentId;

    @OneToOne(mappedBy = "hogar", fetch = FetchType.EAGER)
    private ClienteResidencial cliente;
    private double latitud;
    private double longitud;
    @ManyToOne(cascade = CascadeType.ALL)
    private Transformador transformador;

    @Transient
    private PointValuePair recomendacion;

    public Hogar(ClienteResidencial cliente, double latitud, double longitud, Transformador transformador) {
        this.cliente = cliente;
        this.latitud = latitud;
        this.longitud = longitud;
        this.transformador = transformador;
        transformador.agregarHogar(this);
        cliente.setHogar(this);
    }

    public void obtenerRecomendacion() {
        this.recomendacion = this.transformador.calcularRecomendacion(this.cliente);
    }

    BigDecimal consumoTotalEnHogar(LocalDateTime startTime, LocalDateTime endTime) {
        return cliente.calcularConsumoEnUnPeriodo(startTime, endTime);
    }

    public void mostrarRecomendacion() {
        int i = 0;
        System.out.println("-------------------------------------------------");
        System.out.println("    Recomendacion por Dispositivo                ");
        System.out.println("-------------------------------------------------");
        for (Dispositivo dispositivo : this.cliente.dispositivos) {
            System.out.println(dispositivo.getNombreDelDispositivo() + ": " + this.recomendacion.getPoint()[i] + " Horas");
            i++;
        }
        System.out.println("-------------------------------------------------");
    }

    BigDecimal obtenerConsumosDeDispositivosPorHora() {
        BigDecimal energia = new BigDecimal(0);
        for (Dispositivo dispositivo : cliente.getDispositivos()){
            energia = energia.add(dispositivo.kwQueConsumePorHora);
        }
        return energia;
    }

    public void ejecucionAutomatica(){
        System.out.println("Se esta ejecutando el simplex del hogar de " + this.getCliente().nombreDeUsuario);
        this.obtenerRecomendacion();
        this.mostrarRecomendacion();

    }
}
