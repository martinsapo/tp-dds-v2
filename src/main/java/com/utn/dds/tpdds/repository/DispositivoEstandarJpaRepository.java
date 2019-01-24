package com.utn.dds.tpdds.repository;

import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.model.DispositivoEstandar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DispositivoEstandarJpaRepository extends JpaRepository<DispositivoEstandar, Integer> {
    List<DispositivoEstandar> findDispositivoEstandarByDueno(ClienteResidencial cliente);
}