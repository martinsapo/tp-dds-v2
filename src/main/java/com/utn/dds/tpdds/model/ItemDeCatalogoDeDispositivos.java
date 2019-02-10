package com.utn.dds.tpdds.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ItemDeCatalogoDeDispositivos {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String nombre;
    private BigDecimal consumo;
    private Integer usoMinimo;
    private Integer usoMaximo;
    private Boolean esInteligente;
    private Boolean esBajoConsumo;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "itemDeCatalogoDeDispositivos")
    private List<Dispositivo> dispositivosAsociados;

    public ItemDeCatalogoDeDispositivos(String nombre, BigDecimal consumo ,Integer usoMinimo, Integer usoMaximo, Boolean esBajoConsumo, Boolean esInteligente){
        this.nombre = nombre;
        this.consumo = consumo;
        this.usoMinimo = usoMinimo;
        this.usoMaximo = usoMaximo;
        this.esBajoConsumo = esBajoConsumo;
        this.esInteligente = esInteligente;
        dispositivosAsociados = new ArrayList<>();

    }

    public ItemDeCatalogoDeDispositivos(JSONObject jsonDispositivoDeCatalogo) {
        dispositivosAsociados = new ArrayList<>();
        if (jsonDispositivoDeCatalogo != null) {
            this.nombre = jsonDispositivoDeCatalogo.get("nombre").toString();
            this.consumo = new BigDecimal(jsonDispositivoDeCatalogo.get("consumo").toString());
            this.usoMinimo = new Integer(jsonDispositivoDeCatalogo.get("usoMinimo").toString());
            this.usoMaximo = new Integer(jsonDispositivoDeCatalogo.get("usoMaximo").toString());
            this.esBajoConsumo = Boolean.valueOf(jsonDispositivoDeCatalogo.get("esBajoConsumo").toString());
            this.esInteligente = Boolean.valueOf(jsonDispositivoDeCatalogo.get("esInteligente").toString());;
        }

    }
}
