package com.utn.dds.tpdds.repository;

import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.model.DispositivoInteligente;
import com.utn.dds.tpdds.model.Regla;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReglaRepository {
    @Autowired ReglaJpaRepository reglaJpaRepository;
    @Autowired ClienteResidencialJpaRepository clienteResidencialJpaRepository;
    @Autowired DispositivoInteligenteJpaRepository dispositivoInteligenteJpaRepository;

    public List<Regla> findAll() {
        return reglaJpaRepository.findAll();
    }

    public List<Regla> findAllReglasFromClienteResidencial(ClienteResidencial clienteResidencial) {
        List<DispositivoInteligente> dispositivoInteligentes = dispositivoInteligenteJpaRepository.findDispositivoInteligentesByDueno(clienteResidencial);
        List<Regla> reglas = new ArrayList<>();
        for (DispositivoInteligente dispositivoInteligente : dispositivoInteligentes) {
            reglas.addAll(reglaJpaRepository.findReglaByDispositivoInteligente(dispositivoInteligente));
        }
        return reglas;
    }

    public void deleteById(Integer id) {
        reglaJpaRepository.deleteById(id);
    }

    public void save(Regla regla) {
        reglaJpaRepository.save(regla);
    }
}
