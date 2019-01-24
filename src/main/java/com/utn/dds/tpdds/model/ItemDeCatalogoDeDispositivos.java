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
    private Boolean esInteligente;
    private Boolean esBajoConsumo;
    private BigDecimal consumo;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "itemDeCatalogoDeDispositivos")
    private List<Dispositivo> dispositivosAsociados;

    public ItemDeCatalogoDeDispositivos(String nombre, Boolean esInteligente, Boolean esBajoConsumo,BigDecimal consumo){
        this.nombre=nombre;
        this.esInteligente=esInteligente;
        this.esBajoConsumo=esBajoConsumo;
        this.consumo=consumo;
        dispositivosAsociados = new ArrayList<>();

    }

    public ItemDeCatalogoDeDispositivos(JSONObject jsonDispositivoDeCatalogo) {
        if (jsonDispositivoDeCatalogo != null) {
            this.nombre = jsonDispositivoDeCatalogo.get("nombre").toString();
            this.esInteligente = (Boolean) jsonDispositivoDeCatalogo.get("esInteligente");
            this.esBajoConsumo = (Boolean) jsonDispositivoDeCatalogo.get("esBajoConsumo");
            this.consumo = new BigDecimal(jsonDispositivoDeCatalogo.get("consumo").toString());
        }

    }
}
