package com.utn.dds.tpdds.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.joda.time.DateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ClienteResidencial{

    @javax.persistence.Id @javax.persistence.GeneratedValue(strategy= GenerationType.IDENTITY) @org.springframework.data.annotation.Transient
    private Integer id;

    @org.springframework.data.annotation.Id @javax.persistence.Transient
    private String documentId;

    protected String nombre;
    String apellido;
    String domicilio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Transient DateTime fechaDeAlta;
    String nombreDeUsuario;
    String password;
    private Integer puntos;

    @OneToOne(cascade = CascadeType.ALL) private Hogar hogar;

    @OneToOne(cascade = CascadeType.ALL) private Documento documento;
    private String telefono;
    @OneToOne(cascade = CascadeType.ALL) private Categoria categoria;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "dueno")
    List<Dispositivo> dispositivos = new ArrayList<>();

    private Boolean ahorroAutomatico = false;

    public ClienteResidencial(String nombre, String apellido, String domicilio,
            String nombreDeUsuario, String password, Documento documento, String telefono, Categoria categoria) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.domicilio = domicilio;
        this.nombreDeUsuario = nombreDeUsuario;
        this.password = password;
        this.documento = documento;
        this.telefono = telefono;
        this.categoria = categoria;
        this.ahorroAutomatico = true;
    }

    private void addPoints(Integer points) {
        puntos += points;
    }

    public DispositivoInteligente agregarAdaptadorADispositivo(
            DispositivoEstandar dispositivoEstandar) {
        addPoints(10);
        dispositivoEstandar.convertidoAInteligente();
        return agregarAdaptadorAlDispositivo(dispositivoEstandar);
    }

    public DispositivoEstandar quitarAdaptadorADispositivo(
            DispositivoInteligente dispositivoInteligente) {
        return quitarAdaptadorAlDispositivo(dispositivoInteligente);

    }

    public void agregarDispositivo(Dispositivo dispositivo) {
        addDispositivo(dispositivo);
        addPoints(dispositivo.getPuntosASumar());
    }

    public void addDispositivo(Dispositivo dispositivo) {
        dispositivos.add(dispositivo);
    }

    private DispositivoInteligente agregarAdaptadorAlDispositivo(
            DispositivoEstandar dispositivo) {
        Integer index = dispositivos.indexOf(dispositivo);
        DispositivoInteligente dispositivoIntegente = new DispositivoInteligente(
                dispositivo);
        dispositivos.set(index, dispositivoIntegente);
        return dispositivoIntegente;
    }

    private DispositivoEstandar quitarAdaptadorAlDispositivo(
            DispositivoInteligente dispositivo) {
        Integer index = dispositivos.indexOf(dispositivo);
        DispositivoEstandar dispositivoEstandar = new DispositivoEstandar(
                dispositivo);
        dispositivos.set(index, dispositivoEstandar);
        return dispositivoEstandar;
    }

    public void deleteDispositivo(Dispositivo dispositivo) {
        dispositivos.remove(dispositivo);
    }

    public Integer obtenerCantidadDeDispositivosEncedidos() {
        Integer contador = 0;
        for (Dispositivo dispositivo : dispositivos) {
            if (dispositivo instanceof DispositivoInteligente &&
                    ((DispositivoInteligente) dispositivo).obtenerEstadoDelDispositivo()
                            == EstadoDeDispositivo.PRENDIDO) {
                contador++;
            }
        }
        return contador;
    }

    public Integer obtenerCantidadDeDispositivosApagados() {
        Integer contador = 0;
        for (Dispositivo dispositivo : dispositivos) {
            if (dispositivo instanceof DispositivoInteligente &&
                    ((DispositivoInteligente) dispositivo).obtenerEstadoDelDispositivo()
                            == EstadoDeDispositivo.APAGADO) {
                contador++;
            }
        }
        return contador;
    }

    public Dispositivo buscarDispositivoPorNombre(String nombre) {
        Dispositivo dispositivoEncontrado = null;
        for (Dispositivo dispositivo : dispositivos) {
            if (dispositivo.nombreDelDispositivo.equals(nombre)) {
                dispositivoEncontrado = dispositivo;
            }
        }
        return dispositivoEncontrado;
    }

    public BigDecimal calcularConsumoEnUnPeriodo(LocalDateTime startTime, LocalDateTime endTime) {
        BigDecimal consumos = new BigDecimal(0);
        Hibernate.initialize(getDispositivos());
        for (Dispositivo dispositivo : getDispositivos()) {
            consumos = consumos.add(dispositivo
                    .cantidadDeEnergiaConsumidaEnUnPeriodo(startTime, endTime));
        }
        return consumos;
    }

    public List<Regla> obtenerReglasDeTodosLosDispositivos() {
        List<Regla> reglas = new ArrayList<>();
        for (DispositivoInteligente dispositivoInteligente : getDispositivosInteligentes()) {
            reglas.addAll(dispositivoInteligente.getReglas());
        }
        return reglas;
    }

    public List<DispositivoInteligente> getDispositivosInteligentes() {
        List<DispositivoInteligente> dispositivoInteligentes = new ArrayList<>();
        Hibernate.initialize(getDispositivos());
        for (Dispositivo dispositivoInteligente : getDispositivos()) {
            if (dispositivoInteligente instanceof DispositivoInteligente) {
                dispositivoInteligentes.add((DispositivoInteligente) dispositivoInteligente);
            }
        }
        return dispositivoInteligentes;
    }

    public List<DispositivoEstandar> getDispositivosEstandars() {
        List<DispositivoEstandar> dispositivoEstandars = new ArrayList<>();
        for (Dispositivo dispositivoEstandar : getDispositivos()) {
            if (dispositivoEstandar instanceof DispositivoEstandar) {
                dispositivoEstandars.add((DispositivoEstandar) dispositivoEstandar);
            }
        }
        return dispositivoEstandars;
    }

    public BigDecimal obtenerTodosLosConsumosDeTodosLosDispositivosEnElUltimoMes() {
        LocalDateTime initial = LocalDateTime.now();
        LocalDateTime start = initial.withDayOfMonth(1);
        LocalDateTime end = initial.withDayOfMonth(30);

        BigDecimal consumos = new BigDecimal(0);
        for (DispositivoInteligente dispositivoInteligente : getDispositivosInteligentes()) {
            consumos = consumos.add(dispositivoInteligente
                    .cantidadDeEnergiaConsumidaEnUnPeriodo(start, end));
        }
        return consumos;
    }

    public List<Medicion> obtenerTodasLasMedicionesDeTodosLosDispositivos() {
        List<Medicion> medicions = new ArrayList<>();
        for (Dispositivo dispositivo : getDispositivos()) {
            if (dispositivo instanceof DispositivoInteligente) {
                List<Medicion> mediciones = ((DispositivoInteligente) dispositivo).obtenerTodasLasMediciones();
                medicions.addAll(mediciones);

            }
        }
        return medicions;
    }

    public Boolean passwordMatch(String password) {
        return Objects.equals(this.password, password);
    }
}
