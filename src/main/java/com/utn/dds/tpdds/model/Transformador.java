package com.utn.dds.tpdds.model;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
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
    private List<TablaDeDispositivos> tablaDeDispositivos = new ArrayList<>();

    @Transient
    private double consumo;

    public Transformador(ZonaGeografica zona, Double latitud, Double longitud) {
        this.zona = zona;
        this.latitud = latitud;
        this.longitud = longitud;
        this.zona = zona;
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

    public void agregarTablaDeDispositivos(String jsonTablaDeDispositivos){
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, TablaDeDispositivos.class);
        try{
            this.tablaDeDispositivos = mapper.readValue(new File(jsonTablaDeDispositivos),type);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<TablaDeDispositivos> getTablaDeDispositivos() {
        return this.tablaDeDispositivos;
    }

    PointValuePair calcularRecomendacion(ClienteResidencial cliente) {
        SimplexFacade simplexFacade = new SimplexFacade(GoalType.MAXIMIZE, true);
        ArrayList<Double> arr = crearArrayConConsumos(cliente.dispositivos);
        double[] consumos = aArray(arr);
        simplexFacade.crearFuncionEconomica(calcularCoeficientesParaFuncionEconomica(arr));
        simplexFacade.agregarRestriccion(Relationship.LEQ, 440640, consumos);
        agregarRestricciones(simplexFacade,cliente.dispositivos, consumos);
        try {
            PointValuePair solucion = simplexFacade.resolver();

            LocalDateTime startOfMonth = LocalDateTime.of(2018,1,1,0,0);//MODIFICO PARA ADAPTAR A LOCALDATETIME DE JAVA
            if (cliente.getAhorroAutomatico()) {
                for (int i = 0; i < cliente.dispositivos.size(); i++) {
                    Dispositivo dispositivo = cliente.dispositivos.get(i);
                    if(dispositivo instanceof DispositivoInteligente &&
                            ((DispositivoInteligente) dispositivo).cantidadDeEnergiaConsumidaEnUnPeriodo(startOfMonth, LocalDateTime.now()).doubleValue() >= solucion.getPoint()[0]) {//MODIFICO PARA ADAPTAR A LOCALDATETIME DE JAVA
                        System.out.println("Consumo Dispositivo: " + ((DispositivoInteligente) dispositivo).cantidadDeEnergiaConsumidaEnUnPeriodo(startOfMonth, LocalDateTime.now()).doubleValue());
                        System.out.println("Consumo Simplex Permitido: " + solucion.getPoint()[i]);
                        ((DispositivoInteligente) dispositivo).apagar();
                    }
                }
            }
            return solucion;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    private void setToZero(double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }
    }

    private void agregarRestricciones(SimplexFacade simplexFacade, List<Dispositivo> dispositivos, double[] consumos) {
        double[] coeficiente = new double[consumos.length];
        setToZero(coeficiente);
        double usoMinimo = 0;
        double usoMaximo = 0;
        int i = 0;
        for (Dispositivo dispositivo : dispositivos) {
            String aBuscar = dispositivo.nombreDelDispositivo;
            for (TablaDeDispositivos table : this.tablaDeDispositivos) {
                if (table.getNombre().equals(aBuscar)) {
                    usoMaximo = table.getUsoMaximo();
                    usoMinimo = table.getUsoMinimo();
                    coeficiente[i] = 1;
                    simplexFacade.agregarRestriccion(Relationship.GEQ, usoMinimo, coeficiente);
                    simplexFacade.agregarRestriccion(Relationship.LEQ, usoMaximo, coeficiente);
                    i++;
                    setToZero(coeficiente);
                }
            }
        }
    }

    private ArrayList<Double> crearArrayConConsumos(List<Dispositivo> dispositivos) {
        ArrayList<Double> arr = new ArrayList<>();
        for (Dispositivo dispositivo : dispositivos) {
            String aBuscar = dispositivo.getItemDeCatalogoDeDispositivos().getNombre();
            for (TablaDeDispositivos tabla : this.tablaDeDispositivos) {
                if (tabla.getNombre().equals(aBuscar)) {
                    arr.add(tabla.getConsumo());
                }

            }
        }
        return arr;
    }

    private double[] aArray(ArrayList<Double> consumos) {
        double[] target = new double[consumos.size()];
        for (int i = 0; i < consumos.size(); i++) {
            target[i] = consumos.get(i);
        }
        return target;
    }

    private double[] calcularCoeficientesParaFuncionEconomica(
            ArrayList<Double> consumos) {
        double[] target = new double[consumos.size()];
        for (int i = 0; i < consumos.size(); i++) {
            target[i] = 1;
        }
        return target;
    }
}
