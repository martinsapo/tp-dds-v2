package com.utn.dds.tpdds.repository;

import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.model.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DispositivoJpaRepository extends JpaRepository<Dispositivo, Integer>{
    List<Dispositivo> findDispositivoByDueno(ClienteResidencial clienteResidencial);
}
