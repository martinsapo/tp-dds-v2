package com.utn.dds.tpdds.repository;

import com.utn.dds.tpdds.model.reportes.ReporteDeConsumoTotalPorHogar;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReporteDeConsumoTotalPorHogarMongoRepository extends MongoRepository<ReporteDeConsumoTotalPorHogar, String> {
}
