package com.utn.dds.tpdds.model;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

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

    public ItemDeCatalogoDeDispositivos(String nombre, Boolean esInteligente, Boolean esBajoConsumo,BigDecimal consumo){
        this.nombre=nombre;
        this.esInteligente=esInteligente;
        this.esBajoConsumo=esBajoConsumo;
        this.consumo=consumo;

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
