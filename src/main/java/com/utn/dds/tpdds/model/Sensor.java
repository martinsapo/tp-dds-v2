package com.utn.dds.tpdds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@Getter
@Setter
public abstract class Sensor implements Serializable{
    @Id @GeneratedValue(strategy= GenerationType.TABLE)
    private Integer id;

    @OneToMany(mappedBy = "sensor")
    List<Medicion> mediciones = new ArrayList<>();

    public Sensor(DispositivoInteligente dispositivoInteligente, String nombreDelSensor) {
       this.dispositivoInteligente = dispositivoInteligente;
       dispositivoInteligente.agregarSensor(this);
       this.nombreDelSensor = nombreDelSensor;
    }

    public abstract Object medir();

    @ManyToOne
    private DispositivoInteligente dispositivoInteligente;

    private String nombreDelSensor;

    public void agregarMedicion(Medicion medicion) {
        mediciones.add(medicion);
    }
}
