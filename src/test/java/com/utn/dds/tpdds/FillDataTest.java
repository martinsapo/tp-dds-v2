package com.utn.dds.tpdds;

import com.utn.dds.tpdds.model.Accion;
import com.utn.dds.tpdds.model.AccionesPosibles;
import com.utn.dds.tpdds.model.Categoria;
import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.model.CondicionPorValor;
import com.utn.dds.tpdds.model.DispositivoInteligente;
import com.utn.dds.tpdds.model.Documento;
import com.utn.dds.tpdds.model.EstadoDeDispositivo;
import com.utn.dds.tpdds.model.Hogar;
import com.utn.dds.tpdds.model.Operador;
import com.utn.dds.tpdds.model.Regla;
import com.utn.dds.tpdds.model.Sensor;
import com.utn.dds.tpdds.model.SensorDeValores;
import com.utn.dds.tpdds.model.TipoDeDocumento;
import com.utn.dds.tpdds.model.Transformador;
import com.utn.dds.tpdds.model.ZonaGeografica;
import com.utn.dds.tpdds.repository.ClienteResidencialJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class FillDataTest {

    @Autowired ClienteResidencialJpaRepository clienteResidencialJPARepository;

    public FillDataTest() {}

    @Test
    public void testFillData() {
        ZonaGeografica zonaGeografica = new ZonaGeografica("asd", 1.0,1.0,1.0);
        Transformador transformador = new Transformador(zonaGeografica, -34.612985, -58.470673);

        ClienteResidencial clienteResidencial = new ClienteResidencial("Martin", "Saposnic", "gaona", "martinsapo", "123", new Documento("40136136", TipoDeDocumento.DNI), "123123123", new Categoria(100.0));
        Hogar hogar = new Hogar(clienteResidencial, 1.0, 1.0, transformador);

        DispositivoInteligente dispositivoInteligente = new DispositivoInteligente("nombre", new BigDecimal(12.0), clienteResidencial);


        dispositivoInteligente.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 11, 12,0,0), EstadoDeDispositivo.PRENDIDO);
        dispositivoInteligente.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 11, 13,0,0), EstadoDeDispositivo.APAGADO);
        dispositivoInteligente.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 11, 14,0,0), EstadoDeDispositivo.PRENDIDO);

        Sensor sensor = new SensorDeValores(dispositivoInteligente, "sensor de temperatura");

        Accion accion = new Accion(AccionesPosibles.PRENDER);
        CondicionPorValor condicion = new CondicionPorValor(1.0, Operador.MAYOR);

        Regla regla = new Regla(condicion, accion, dispositivoInteligente, sensor);

        clienteResidencial.addDispositivo(dispositivoInteligente);

        clienteResidencialJPARepository.save(clienteResidencial);


    }
}
