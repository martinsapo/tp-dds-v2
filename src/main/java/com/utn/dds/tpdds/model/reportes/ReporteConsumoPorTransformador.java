package com.utn.dds.tpdds.model.reportes;

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
public class ReporteConsumoPorTransformador {
    @Id
    public String documentId;

    public Map<String, BigDecimal> mapaDeIdYConsumo = new HashMap<>();

    public LocalDateTime fechaInicio;
    public LocalDateTime fechaFin;

    public ReporteConsumoPorTransformador(List<ZonaGeografica> zonaGeograficas, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.documentId = "reporte_de_consumo_por_transformador_" + fechaInicio.toString() + "_" + fechaFin.toString();

        List<Transformador> transformadores = new ArrayList<>();

        for (ZonaGeografica zonaGeografica : zonaGeograficas) {
            transformadores.addAll(zonaGeografica.getListaDeTransformadores());
        }

        for (Transformador transformador : transformadores) {
            mapaDeIdYConsumo.put(transformador.getId().toString(), transformador.calcularConsumoEnUnPeriodo(fechaInicio, fechaFin));
        }

        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
}
