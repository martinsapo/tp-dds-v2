package com.utn.dds.tpdds.repository;

import com.utn.dds.tpdds.model.ItemDeCatalogoDeDispositivos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogoDispositivosJpaRepository extends JpaRepository<ItemDeCatalogoDeDispositivos, Integer> {
}
