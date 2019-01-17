package com.utn.dds.tpdds.repository;

import com.utn.dds.tpdds.model.ClienteResidencial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteResidencialJpaRepository extends JpaRepository<ClienteResidencial, Integer> {
    ClienteResidencial findClienteResidencialByNombreDeUsuario(String nombreDeUsuario);
}