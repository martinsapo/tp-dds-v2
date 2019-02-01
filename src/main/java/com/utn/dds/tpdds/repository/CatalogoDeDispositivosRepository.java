package com.utn.dds.tpdds.repository;

import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.model.DispositivoInteligente;
import com.utn.dds.tpdds.model.ItemDeCatalogoDeDispositivos;
import com.utn.dds.tpdds.model.Regla;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CatalogoDeDispositivosRepository {
    @Autowired CatalogoDispositivosJpaRepository catalogoDispositivosJpaRepository;

    public void findDispositivoDeCatalogoById(Integer id) {
        catalogoDispositivosJpaRepository.findDispositivoDeCatalogoById(id);
    }

    public List<ItemDeCatalogoDeDispositivos> findAll() {
        return catalogoDispositivosJpaRepository.findAll();
    }

}
