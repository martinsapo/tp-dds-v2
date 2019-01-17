package com.utn.dds.tpdds.model;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@JsonDeserialize(as=DispositivoInteligente.class)
@Getter
@Setter
public class CatalogoDispositivos {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Integer id;
    private String nombre;
    private Boolean esInteligente;
    private Boolean esBajoConsumo;
    private BigDecimal consumo;


    public CatalogoDispositivos(){};
    public CatalogoDispositivos(String nombre, Boolean esInteligente, Boolean esBajoConsumo,BigDecimal consumo){
        this.nombre=nombre;
        this.esInteligente=esInteligente;
        this.esBajoConsumo=esBajoConsumo;
        this.consumo=consumo;

    }

    public CatalogoDispositivos(JSONObject jsonDispositivoDeCatalogo) {
//CatalogoDispositivos dispositivoDeCatalogo = (CatalogoDispositivos) Helper.mapJsonToObject(jsonDispositivoDeCatalogo, this.getClass());

        if (jsonDispositivoDeCatalogo != null) {
            this.nombre = jsonDispositivoDeCatalogo.get("nombre").toString();
            this.esInteligente = (Boolean) jsonDispositivoDeCatalogo.get("esInteligente");
            this.esBajoConsumo = (Boolean) jsonDispositivoDeCatalogo.get("esBajoConsumo");
            this.consumo = new BigDecimal(jsonDispositivoDeCatalogo.get("consumo").toString());
        }

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreEquipoConcreto() {
        return nombre;
    }

    public void setNombreEquipoConcreto(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEsInteligente() {
        return esInteligente;
    }

    public void setEsInteligente(Boolean esInteligente) {
        this.esInteligente = esInteligente;
    }

    public Boolean getEsBajoConsumo() {
        return esBajoConsumo;
    }

    public void setEsBajoConsumo(Boolean esBajoConsumo) {
        this.esBajoConsumo = esBajoConsumo;
    }

    public BigDecimal getConsumo() {
        return consumo;
    }

    public void setConsumo(BigDecimal consumo) {
        this.consumo = consumo;
    }
}
