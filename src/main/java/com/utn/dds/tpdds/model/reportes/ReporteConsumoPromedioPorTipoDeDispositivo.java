package com.utn.dds.tpdds.model.reportes;

import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.model.DispositivoEstandar;
import com.utn.dds.tpdds.model.DispositivoInteligente;
import com.utn.dds.tpdds.model.Hogar;
import com.utn.dds.tpdds.model.Transformador;
import com.utn.dds.tpdds.model.ZonaGeografica;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
public class ReporteConsumoPromedioPorTipoDeDispositivo {
    @Id
    public String documentId;

    public Double consumoPromedioEstandar;
    public Double consumoPromedioInteligente;

    public LocalDateTime fechaInicio;
    public LocalDateTime fechaFin;

    public ReporteConsumoPromedioPorTipoDeDispositivo(List<ZonaGeografica> zonaGeograficas, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.documentId = "reporte_de_consumo_promedio_por_tipo_de_dispositivo_" + fechaInicio.toString() + "_" + fechaFin.toString();

        double consumoEstandar = 0;
        double consumoInteligente = 0;

        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;

        List<Hogar> hogares = obtenerHogares(zonaGeograficas);
        List<ClienteResidencial> clientes = new ArrayList<>();

        for(Hogar hogar : hogares) {
            clientes.add(hogar.getCliente());
        }

        List<DispositivoEstandar> dispositivoEstandars = new ArrayList<>();
        List<DispositivoInteligente> dispositivoInteligentes = new ArrayList<>();

        for (ClienteResidencial cliente : clientes) {
            dispositivoEstandars.addAll(cliente.getDispositivosEstandars());
            dispositivoInteligentes.addAll(cliente.getDispositivosInteligentes());
        }

        for (DispositivoEstandar dispositivoEstandar : dispositivoEstandars) {
            consumoEstandar += dispositivoEstandar.cantidadDeEnergiaConsumidaEnUnPeriodo(fechaInicio, fechaFin).doubleValue();
        }

        for (DispositivoInteligente dispositivoInteligente : dispositivoInteligentes) {
            consumoInteligente += dispositivoInteligente.cantidadDeEnergiaConsumidaEnUnPeriodo(fechaInicio, fechaFin).doubleValue();
        }

        if(dispositivoEstandars.size() > 0) {
            consumoPromedioEstandar = consumoEstandar / dispositivoEstandars.size();
        }

        if(dispositivoInteligentes.size() > 0) {
            consumoPromedioInteligente = consumoInteligente / dispositivoInteligentes.size();
        }
    }

    private List<Hogar> obtenerHogares(List<ZonaGeografica> zonasGeograficas) {
        List<Hogar> hogares = new ArrayList<>();

        for (ZonaGeografica zonaGeografica : zonasGeograficas) {
            for (Transformador transformador : zonaGeografica.getListaDeTransformadores()) {
                hogares.addAll(transformador.getListaDeHogares());
            }
        }

        return hogares;
    }

    @Override public String toString() {
        return "ReporteConsumoPromedioPorTipoDeDispositivo{" + "documentId='"
                + documentId + '\'' + ", consumoPromedioEstandar="
                + consumoPromedioEstandar + ", consumoPromedioInteligente="
                + consumoPromedioInteligente + ", fechaInicio=" + fechaInicio
                + ", fechaFin=" + fechaFin + '}';
    }
}
