package com.utn.dds.tpdds.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@JsonDeserialize(as=DispositivoInteligente.class)
@Getter
@Setter
public class DispositivoInteligente extends Dispositivo implements Serializable{

    @OneToMany(orphanRemoval = true, cascade= CascadeType.ALL, mappedBy = "dispositivoInteligente")
    private List<RegistroDeCambioDeEstadoDeDispositivo> registrosDeCambioDeEstadoDeDispositivo = new ArrayList<>();

    @Transient
    private DispositivoState state = new StateApagado();

    @OneToOne(cascade = CascadeType.ALL)
    private Driver driver;

    @OneToMany(mappedBy = "dispositivoInteligente", cascade = CascadeType.ALL)
    private List<Regla> reglas = new ArrayList<>();

    @OneToMany(orphanRemoval = true, cascade= CascadeType.ALL, mappedBy = "dispositivoInteligente")
    private List<Sensor> sensores = new ArrayList<>();

    public DispositivoInteligente(String nombre, BigDecimal kwPorHora, ClienteResidencial dueno) {
        super(nombre, kwPorHora, dueno);
        agregarRegistroARegistrosDeCambioDeEstadoDeDispositivo(EstadoDeDispositivo.APAGADO);
    }

    public DispositivoInteligente(DispositivoEstandar dispositivo) {
        super(dispositivo.nombreDelDispositivo, dispositivo.kwQueConsumePorHora, dispositivo.dueno);
        agregarRegistroARegistrosDeCambioDeEstadoDeDispositivo(EstadoDeDispositivo.APAGADO);
    }

    public DispositivoInteligente(String jsonDispositivo) {
        DispositivoInteligente dispositivo = (DispositivoInteligente) Helper.mapJsonToObject(jsonDispositivo, this.getClass());
        if (dispositivo != null) {
            this.nombreDelDispositivo = dispositivo.nombreDelDispositivo;
            this.state = dispositivo.state;
            this.kwQueConsumePorHora = dispositivo.kwQueConsumePorHora;
            this.dueno = dispositivo.dueno;
            agregarRegistroARegistrosDeCambioDeEstadoDeDispositivo(EstadoDeDispositivo.APAGADO);
        }
    }

  public DispositivoInteligente(String nombre, BigDecimal kwPorHora){
      this.nombreDelDispositivo = nombre;
      this.kwQueConsumePorHora = kwPorHora;
  }

    public DispositivoInteligente() {
    }

    public void encender() {
        state.encender(this);
    }

    public void apagar() {
        state.apagar(this);
    }

    public void cambiarAAhorroDeEnergia() {
        state.modoAhorro(this);
    }

    public EstadoDeDispositivo obtenerEstadoDelDispositivo() {
        return state.getEstado();
    }

    public Integer getPuntosASumar() {
        return 15;
    }

    public void agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime dateTime, EstadoDeDispositivo estadoDeDispositivo) {
        registrosDeCambioDeEstadoDeDispositivo.add(new RegistroDeCambioDeEstadoDeDispositivo(dateTime, estadoDeDispositivo, this));
        ejecutarReglas();
    }


    public BigDecimal cantidadDeEnergiaConsumidaEnUnPeriodo(LocalDateTime startTime, LocalDateTime endTime) {
        BigDecimal cantidadDeHorasEncendida = new BigDecimal(0);
        BigDecimal energiaConsumidaEnPeriodo;

        Collections.sort(registrosDeCambioDeEstadoDeDispositivo, new RegistroDeCambioDeEstadoComparator());

        ArrayList<RegistroDeCambioDeEstadoDeDispositivo> registrosQueSucedieronEnLasUltimasHoras = filtrarListaDeRegistrosEntreDosFechas(startTime, endTime);

        int indexStart = -1;
        for(int i = 0; i < registrosDeCambioDeEstadoDeDispositivo.size(); i++) {
            if(registrosDeCambioDeEstadoDeDispositivo.get(i).getTimestamp().isBefore(registrosQueSucedieronEnLasUltimasHoras.get(0).getTimestamp())) {
                indexStart = i;
            }
        }

        int indexEnd = -1;
        for(int i = registrosDeCambioDeEstadoDeDispositivo.size() - 1; i >= 0; i--) {
            if(registrosDeCambioDeEstadoDeDispositivo.get(i).getTimestamp().isAfter(registrosQueSucedieronEnLasUltimasHoras.get(registrosQueSucedieronEnLasUltimasHoras.size()-1).getTimestamp())) {
                indexEnd = i;
            }
        }

        if (indexStart != -1) {
            registrosQueSucedieronEnLasUltimasHoras.add(0, registrosDeCambioDeEstadoDeDispositivo.get(indexStart));
            if(registrosQueSucedieronEnLasUltimasHoras.get(0).getTimestamp().isBefore(startTime)) {
                registrosQueSucedieronEnLasUltimasHoras.get(0).setTimestamp(startTime);
            }
        }

        if (indexEnd != -1) {
            registrosQueSucedieronEnLasUltimasHoras.add(registrosDeCambioDeEstadoDeDispositivo.get(indexEnd));
            if(registrosQueSucedieronEnLasUltimasHoras.get(registrosQueSucedieronEnLasUltimasHoras.size()-1).getTimestamp().isAfter(endTime)) {
                registrosQueSucedieronEnLasUltimasHoras.get(registrosQueSucedieronEnLasUltimasHoras.size()-1).setTimestamp(endTime);
            }
        }

        for (int i = 0; i < registrosQueSucedieronEnLasUltimasHoras.size()-1; i++) {
            RegistroDeCambioDeEstadoDeDispositivo registro = registrosQueSucedieronEnLasUltimasHoras.get(i);
            RegistroDeCambioDeEstadoDeDispositivo siguienteRegistro = registrosQueSucedieronEnLasUltimasHoras.get(i+1);
            if (registro.seRegistroUnEncendido()) {
                cantidadDeHorasEncendida = cantidadDeHorasEncendida.add(siguienteRegistro.diferenciaEntreTiempos(registro));
            }
        }

        energiaConsumidaEnPeriodo= cantidadDeHorasEncendida.multiply(kwQueConsumePorHora);

        return energiaConsumidaEnPeriodo;
    }

    BigDecimal cantidadDeEnergiaConsumidaEnElUltimoMes() {
        LocalDateTime initial = LocalDateTime.now();
        LocalDateTime start = initial.withDayOfMonth(1);
        LocalDateTime end = initial.withDayOfMonth(30);
        return cantidadDeEnergiaConsumidaEnUnPeriodo(start, end);
    }

    void agregarRegistroARegistrosDeCambioDeEstadoDeDispositivo(EstadoDeDispositivo estado) {
        registrosDeCambioDeEstadoDeDispositivo.add(new RegistroDeCambioDeEstadoDeDispositivo(LocalDateTime.now(), estado, this));
        ejecutarReglas();
    }

    public void ejecutarReglas() {
        List<Accion> accionesARealizar = new ArrayList<>();
        for (Regla regla : reglas) {
            accionesARealizar.add(regla.devolverAccionSiAplica(this));
        }
        for (Accion accion : accionesARealizar){
            accion.apply(driver);
        }
    }

    public void agregarSensor(Sensor sensor) {
        this.sensores.add(sensor);
    }

    void agregarRegla(Regla regla) {
        reglas.add(regla);
    }

    public ArrayList<RegistroDeCambioDeEstadoDeDispositivo> filtrarListaDeRegistrosEntreDosFechas(LocalDateTime startTime, LocalDateTime endTime) {
        ArrayList<RegistroDeCambioDeEstadoDeDispositivo> registrosQueSucedieronEnLasUltimasHoras = new ArrayList<>();
        for (RegistroDeCambioDeEstadoDeDispositivo registro : registrosDeCambioDeEstadoDeDispositivo) {
            if (registro.registroSucedioEntreCiertasFechas(startTime, endTime)) {
                registrosQueSucedieronEnLasUltimasHoras.add(registro);

            }
        }

        return registrosQueSucedieronEnLasUltimasHoras;
    }

    public List<Medicion> obtenerTodasLasMediciones() {
        List<Medicion> mediciones = new ArrayList<>();
        for (Sensor sensor : getSensores()) {
            mediciones.addAll(sensor.mediciones);
        }
        return mediciones;
    }
}