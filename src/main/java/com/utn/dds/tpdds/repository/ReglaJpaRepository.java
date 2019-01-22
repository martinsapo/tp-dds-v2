package com.utn.dds.tpdds.repository;

import com.utn.dds.tpdds.model.DispositivoInteligente;
import com.utn.dds.tpdds.model.Regla;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReglaJpaRepository extends JpaRepository<Regla, Integer>{
    List<Regla> findReglaByDispositivoInteligente(DispositivoInteligente dispositivoInteligente);
}
