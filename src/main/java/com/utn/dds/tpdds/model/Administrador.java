package com.utn.dds.tpdds.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;
import org.joda.time.Days;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Administrador {

    @javax.persistence.Id @javax.persistence.GeneratedValue(strategy= GenerationType.IDENTITY) @org.springframework.data.annotation.Transient
    private Integer id;

    @org.springframework.data.annotation.Id @javax.persistence.Transient
    private String documentId;

    protected String nombre;
    private String apellido;
    private String domicilio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Transient private DateTime fechaDeAlta;
    private String nombreDeUsuario;
    private String password;
    private Integer puntos;

    public Administrador(String jsonAdministrador) {
        super();
        Administrador administrador = (Administrador) Helper.mapJsonToObject(jsonAdministrador, this.getClass());
        if( administrador != null) {
            this.nombre = administrador.nombre;
            this.apellido = administrador.apellido;
            this.domicilio = administrador.domicilio;
            this.fechaDeAlta = administrador.fechaDeAlta;
            this.nombreDeUsuario = administrador.nombreDeUsuario;
            this.password = administrador.password;
        }
    }
    public static ArrayList<ItemDeCatalogoDeDispositivos> catalogo=new ArrayList<>();


    public Integer getAntiguedad() {
        return Days.daysBetween(fechaDeAlta.toLocalDate(), DateTime.now().toLocalDate()).getDays();
    }

    public Boolean passwordMatch(String password) {
        return Objects.equals(this.password, password);
    }
}
