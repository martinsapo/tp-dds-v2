package com.utn.dds.tpdds.repository;

import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.model.Transformador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransformadorJpaRepository extends JpaRepository<Transformador, Integer> {

}
