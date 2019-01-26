package com.utn.dds.tpdds.model.reportes;

import com.utn.dds.tpdds.model.Hogar;
import com.utn.dds.tpdds.model.Transformador;
import com.utn.dds.tpdds.model.ZonaGeografica;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document
@Getter
@Setter
@NoArgsConstructor
public class ReporteDeConsumoTotalPorHogar{
    @Id
    public String documentId;

    public Map<String, BigDecimal> mapaDeNombreYConsumo = new HashMap<>();

    public LocalDateTime fechaInicio;
    public LocalDateTime fechaFin;

    public ReporteDeConsumoTotalPorHogar(List<ZonaGeografica> zonaGeograficas, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.documentId = "reporte_de_consumo_total_por_hogar_" + fechaInicio.toString() + "_" + fechaFin.toString();

        List<Hogar> hogares = obtenerHogares(zonaGeograficas);
        for (Hogar hogar : hogares) {
            mapaDeNombreYConsumo.put(hogar.getCliente().getNombre(), hogar.getCliente().calcularConsumoEnUnPeriodo(fechaInicio, fechaFin));
        }

        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    @Override public String toString() {
        return "ReporteDeConsumoTotalPorHogar{" + "documentId='" + documentId
                + '\'' + ", mapaDeNombreYConsumo=" + mapaDeNombreYConsumo
                + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
                + '}';
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
}
