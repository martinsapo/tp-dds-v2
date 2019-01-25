package com.utn.dds.tpdds.model;

import com.utn.dds.tpdds.repository.RegistroDeCambioDeEstadoDeDispositivoJpaRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RegistroDeCambioDeEstadoDeDispositivo{

    private LocalDateTime timestamp;

    @Column(columnDefinition = "enum('APAGADO','PRENDIDO','MODO_AHORRO')")
    @Enumerated(EnumType.STRING)
    private EstadoDeDispositivo estado;

    @ManyToOne(cascade= CascadeType.ALL)
    private DispositivoInteligente dispositivoInteligente;

    RegistroDeCambioDeEstadoDeDispositivo(LocalDateTime timestamp, EstadoDeDispositivo estado, DispositivoInteligente dispositivoInteligente) {
        this.timestamp = timestamp;
        this.estado = estado;
        this.dispositivoInteligente = dispositivoInteligente;
    }

    Boolean registroSucedioEntreCiertasFechas(LocalDateTime startDate, LocalDateTime endDate) {
        return (timestamp.isBefore(endDate) && timestamp.isAfter(startDate) || timestamp.isBefore(endDate) && timestamp.isEqual(startDate));
    }

    Boolean seRegistroUnEncendido() {
        return estado == EstadoDeDispositivo.PRENDIDO;
    }

    BigDecimal diferenciaEntreTiempos(RegistroDeCambioDeEstadoDeDispositivo registro) {
        return new BigDecimal(((Duration.between(registro.timestamp,this.timestamp)).getSeconds())/3600);
    }

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
}
