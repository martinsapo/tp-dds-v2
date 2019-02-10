package com.utn.dds.tpdds.repository;

import com.utn.dds.tpdds.model.ItemDeCatalogoDeDispositivos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface CatalogoDispositivosJpaRepository extends JpaRepository<ItemDeCatalogoDeDispositivos, Integer> {
    ItemDeCatalogoDeDispositivos findFirstByNombreLike(String nombre);
}
