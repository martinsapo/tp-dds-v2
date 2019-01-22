package com.utn.dds.tpdds.repository;

import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.model.DispositivoInteligente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DispositivoInteligenteJpaRepository extends JpaRepository<DispositivoInteligente, Integer> {
    List<DispositivoInteligente> findDispositivoInteligentesByDueno(ClienteResidencial clienteResidencial);
}
