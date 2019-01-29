package com.utn.dds.tpdds;

import com.utn.dds.tpdds.model.Accion;
import com.utn.dds.tpdds.model.AccionesPosibles;
import com.utn.dds.tpdds.model.Administrador;
import com.utn.dds.tpdds.model.CondicionDeUsoMensual;
import com.utn.dds.tpdds.model.ItemDeCatalogoDeDispositivos;
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
import com.utn.dds.tpdds.repository.AdministradorJpaRepository;
import com.utn.dds.tpdds.repository.CatalogoDispositivosJpaRepository;
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
    @Autowired CatalogoDispositivosJpaRepository catalogoDispositivosJpaRepository;
    @Autowired AdministradorJpaRepository administradorJpaRepository;

    public FillDataTest() {}

    @Test
    public void testFillData() {
        ItemDeCatalogoDeDispositivos disp1 = new ItemDeCatalogoDeDispositivos(
                "Aire de 2200 frigorias", new BigDecimal(1.013));

        ItemDeCatalogoDeDispositivos disp2 = new ItemDeCatalogoDeDispositivos(
                "TV de tubo fluorescente de 21",  new BigDecimal(0.075));
        ItemDeCatalogoDeDispositivos disp3 = (new ItemDeCatalogoDeDispositivos(
                "TV de tubo fluorescente de 29 a 34",  new BigDecimal(0.175)));
        ItemDeCatalogoDeDispositivos disp4 = (new ItemDeCatalogoDeDispositivos("TV LCD de 40",
                 new BigDecimal(0.18)));
        ItemDeCatalogoDeDispositivos disp5 = (new ItemDeCatalogoDeDispositivos("TV LED de 24",
                new BigDecimal(0.04)));
        ItemDeCatalogoDeDispositivos disp6 = (new ItemDeCatalogoDeDispositivos("TV LED de 32",
                new BigDecimal(0.055)));
        ItemDeCatalogoDeDispositivos disp7 = (new ItemDeCatalogoDeDispositivos("TV LED de 40",
                 new BigDecimal(0.08)));

        ItemDeCatalogoDeDispositivos disp8 = (new ItemDeCatalogoDeDispositivos(
                "Heladera con freezer",  new BigDecimal(0.09)));
        ItemDeCatalogoDeDispositivos disp9 = (new ItemDeCatalogoDeDispositivos(
                "Heladera sin freezer", new BigDecimal(0.075)));

        catalogoDispositivosJpaRepository.save(disp1);
        catalogoDispositivosJpaRepository.save(disp2);
        catalogoDispositivosJpaRepository.save(disp3);
        catalogoDispositivosJpaRepository.save(disp4);
        catalogoDispositivosJpaRepository.save(disp5);
        catalogoDispositivosJpaRepository.save(disp6);
        catalogoDispositivosJpaRepository.save(disp7);
        catalogoDispositivosJpaRepository.save(disp8);
        catalogoDispositivosJpaRepository.save(disp9);


        ZonaGeografica zonaGeografica = new ZonaGeografica("asd", 1.0, 1.0, 1.0);
        Transformador transformador = new Transformador(zonaGeografica, -34.612985, -58.470673);

        ClienteResidencial clienteResidencial = new ClienteResidencial("Martin",
                "Saposnic", "gaona", "martinsapo", "123",
                new Documento("40136136", TipoDeDocumento.DNI), "123123123", new Categoria(100.0));
        Hogar hogar = new Hogar(clienteResidencial, 1.0, 1.0, transformador);

        DispositivoInteligente dispositivoInteligente = new DispositivoInteligente(
                "nombre", clienteResidencial, disp9);


        dispositivoInteligente.agregarRegistroDeCambioDeEstadoPersonalizado(
                LocalDateTime.of(2018, 11, 12, 0, 0), EstadoDeDispositivo.PRENDIDO);
        dispositivoInteligente.agregarRegistroDeCambioDeEstadoPersonalizado(
                LocalDateTime.of(2018, 11, 13, 0, 0), EstadoDeDispositivo.APAGADO);
        dispositivoInteligente.agregarRegistroDeCambioDeEstadoPersonalizado(
                LocalDateTime.of(2018, 11, 14, 0, 0), EstadoDeDispositivo.PRENDIDO);

        Sensor sensor = new SensorDeValores(dispositivoInteligente, "sensor de temperatura");

        Accion accion = new Accion(AccionesPosibles.APAGAR);
        CondicionDeUsoMensual condicion = new CondicionDeUsoMensual(360, Operador.MAYOR);

        Regla regla = new Regla(condicion, accion, dispositivoInteligente,
                sensor);

        clienteResidencial.addDispositivo(dispositivoInteligente);

        clienteResidencialJPARepository.save(clienteResidencial);

        Administrador administrador = new Administrador();
        administrador.setPassword("123");
        administrador.setNombreDeUsuario("martinsapo");


        administradorJpaRepository.save(administrador);

    }
}
