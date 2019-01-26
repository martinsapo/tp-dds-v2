package com.utn.dds.tpdds.repository;

import com.utn.dds.tpdds.model.reportes.ReporteConsumoPorTransformador;
import com.utn.dds.tpdds.model.reportes.ReporteConsumoPromedioPorTipoDeDispositivo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReporteDeConsumoPorTransformadorMongoRepository extends MongoRepository<ReporteConsumoPorTransformador, String> {
}
