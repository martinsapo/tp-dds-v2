package com.utn.dds.tpdds.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONObject;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ItemDeCatalogoDeDispositivos {

    @javax.persistence.Id @javax.persistence.GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private BigDecimal consumo;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "itemDeCatalogoDeDispositivos")
    private List<Dispositivo> dispositivosAsociados;

    public ItemDeCatalogoDeDispositivos(String nombre, BigDecimal consumo){
        this.nombre=nombre;
        this.consumo=consumo;
        dispositivosAsociados = new ArrayList<>();

    }

    public ItemDeCatalogoDeDispositivos(JSONObject jsonDispositivoDeCatalogo) {
        dispositivosAsociados = new ArrayList<>();
        if (jsonDispositivoDeCatalogo != null) {
            this.nombre = jsonDispositivoDeCatalogo.get("nombre").toString();
            this.consumo = new BigDecimal(jsonDispositivoDeCatalogo.get("consumo").toString());
        }

    }
}
