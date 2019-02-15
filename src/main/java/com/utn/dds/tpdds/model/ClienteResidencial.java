package com.utn.dds.tpdds.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
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
        for (DispositivoInteligente dispositivo : getDispositivosInteligentes()) {
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

    public BigDecimal obtenerTodosLosConsumosDeTodosLosDispositivosEnEsteMes() {
        BigDecimal consumos = new BigDecimal(0);
        for (Dispositivo dispositivo : dispositivos) {
            consumos = consumos.add(dispositivo.cantidadDeEnergiaConsumidaEnEsteMes());
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

    PointValuePair calcularRecomendacion() {
        SimplexFacade simplexFacade = new SimplexFacade(GoalType.MAXIMIZE, true);
        double[] consumos = crearArrayConConsumos(dispositivos);
        simplexFacade.crearFuncionEconomica(calcularCoeficientesParaFuncionEconomica(consumos));
        simplexFacade.agregarRestriccion(Relationship.LEQ, 612 * 24 * 30, consumos);
        agregarRestricciones(simplexFacade, dispositivos, consumos);
        PointValuePair solucion = simplexFacade.resolver();

        if (this.getAhorroAutomatico()) {
            int i = 0;
            for (DispositivoInteligente dispositivoInteligente : getDispositivosInteligentes()) {
                System.out.println("Analisis para el dispositivo: " + dispositivoInteligente.nombreDelDispositivo);
                System.out.println("Cantidad de horas usado en el ultimo mes: " + dispositivoInteligente.cantidadDeHorasUsadoEnEsteMes().doubleValue());
                System.out.println("Calculo Simplex Permitido: " + solucion.getPoint()[i]);
                if (dispositivoInteligente.cantidadDeHorasUsadoEnEsteMes().doubleValue() >= solucion.getPoint()[i]) {
                    dispositivoInteligente.apagar();
                    System.out.println("se apago pues supero el limite");
                }
                i++;
            }
        }
        return solucion;
    }

    private void agregarRestricciones(SimplexFacade simplexFacade, List<Dispositivo> dispositivos, double[] consumos) {
        double[] coeficiente = new double[consumos.length];
        setToZero(coeficiente);
        int i = 0;
        for (Dispositivo dispositivo : dispositivos) {
            coeficiente[i] = 1;
            simplexFacade.agregarRestriccion(Relationship.GEQ, dispositivo.getItemDeCatalogoDeDispositivos().getUsoMinimo(), coeficiente);
            simplexFacade.agregarRestriccion(Relationship.LEQ, dispositivo.getItemDeCatalogoDeDispositivos().getUsoMaximo(), coeficiente);
            i++;
            setToZero(coeficiente);
        }
    }

    private double[] crearArrayConConsumos(List<Dispositivo> dispositivos) {
        ArrayList<Double> arr = new ArrayList<>();
        for (Dispositivo dispositivo : dispositivos) {
            arr.add(dispositivo.getItemDeCatalogoDeDispositivos().getConsumo().doubleValue());
        }
        double[] target = new double[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            target[i] = arr.get(i);
        }
        return target;

    }

    private double[] calcularCoeficientesParaFuncionEconomica(double[] consumos) {
        double[] target = new double[consumos.length];
        for (int i = 0; i < consumos.length; i++) {
            target[i] = 1;
        }
        return target;
    }

    private void setToZero(double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }
    }
}
