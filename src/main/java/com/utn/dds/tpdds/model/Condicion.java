package com.utn.dds.tpdds.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
public abstract class Condicion implements Serializable {

    @Id @GeneratedValue(strategy= GenerationType.TABLE)
    int id;

    public Condicion(Operador operator) {
        this.operator = operator;
    }

    abstract Boolean applies(DispositivoInteligente dispositivoInteligente, Sensor sensor);

    @OneToOne(mappedBy = "condicion", optional = false)
    private Regla regla;

    @Column(columnDefinition = "enum('FALSE','TRUE','MAYOR','MENOR','IGUAL')")
    @Enumerated(EnumType.STRING)
    protected Operador operator;

    public abstract Object getMedicion();
}
