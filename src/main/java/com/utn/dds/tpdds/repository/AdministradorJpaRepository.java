package com.utn.dds.tpdds.repository;

import com.utn.dds.tpdds.model.Administrador;
import com.utn.dds.tpdds.model.ClienteResidencial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorJpaRepository extends JpaRepository<Administrador, Integer> {
    Administrador findAdministradorByNombreDeUsuario(String nombreDeUsuario);
}
