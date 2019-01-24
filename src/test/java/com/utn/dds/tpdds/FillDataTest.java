package com.utn.dds.tpdds;

import com.utn.dds.tpdds.model.Accion;
import com.utn.dds.tpdds.model.AccionesPosibles;
import com.utn.dds.tpdds.model.Administrador;
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
                "Aire de 2200 frigorias", true, false, new BigDecimal(1.013));

        ItemDeCatalogoDeDispositivos disp2 = new ItemDeCatalogoDeDispositivos(
                "TV de tubo fluorescente de 21", true, false, new BigDecimal(0.075));
        ItemDeCatalogoDeDispositivos disp3 = (new ItemDeCatalogoDeDispositivos(
                "TV de tubo fluorescente de 29 a 34", true, false, new BigDecimal(0.175)));
        ItemDeCatalogoDeDispositivos disp4 = (new ItemDeCatalogoDeDispositivos("TV LCD de 40",
                false, false, new BigDecimal(0.18)));
        ItemDeCatalogoDeDispositivos disp5 = (new ItemDeCatalogoDeDispositivos("TV LED de 24",
                true, true, new BigDecimal(0.04)));
        ItemDeCatalogoDeDispositivos disp6 = (new ItemDeCatalogoDeDispositivos("TV LED de 32",
                true, true, new BigDecimal(0.055)));
        ItemDeCatalogoDeDispositivos disp7 = (new ItemDeCatalogoDeDispositivos("TV LED de 40",
                true, true, new BigDecimal(0.08)));

        ItemDeCatalogoDeDispositivos disp8 = (new ItemDeCatalogoDeDispositivos(
                "Heladera con freezer", true, true, new BigDecimal(0.09)));
        ItemDeCatalogoDeDispositivos disp9 = (new ItemDeCatalogoDeDispositivos(
                "Heladera sin freezer", true, true, new BigDecimal(0.075)));

        ItemDeCatalogoDeDispositivos disp10 = (new ItemDeCatalogoDeDispositivos(
                "Lavarropas automatico de 5kg con calentamiento de agua", false,
                false, new BigDecimal(0.0875)));
        ItemDeCatalogoDeDispositivos disp11 = (new ItemDeCatalogoDeDispositivos(
                "Lavarropas automatico de 5kg", true, true, new BigDecimal(0.175)));
        ItemDeCatalogoDeDispositivos disp12 = (new ItemDeCatalogoDeDispositivos(
                "Lavarropas semiautomatico de 5kg", false, true, new BigDecimal(0.1275)));

        ItemDeCatalogoDeDispositivos disp13 = (new ItemDeCatalogoDeDispositivos(
                "Ventilador de pie", false, true, new BigDecimal(0.09)));
        ItemDeCatalogoDeDispositivos disp14 = (new ItemDeCatalogoDeDispositivos(
                "Ventilador de techo", true, true, new BigDecimal(0.06)));

        ItemDeCatalogoDeDispositivos disp15 = (new ItemDeCatalogoDeDispositivos(
                "Lampara Halogena de 40w", true, false, new BigDecimal(0.04)));
        ItemDeCatalogoDeDispositivos disp16 = (new ItemDeCatalogoDeDispositivos(
                "Lampara Halogena de 60w", true, false, new BigDecimal(0.06)));
        ItemDeCatalogoDeDispositivos disp17 = (new ItemDeCatalogoDeDispositivos(
                "Lampara Halogena de 100w", true, false, new BigDecimal(0.015)));
        ItemDeCatalogoDeDispositivos disp18 = (new ItemDeCatalogoDeDispositivos("Lampara de 11w",
                true, true, new BigDecimal(0.011)));
        ItemDeCatalogoDeDispositivos disp19 = (new ItemDeCatalogoDeDispositivos("Lampara de 15w",
                true, true, new BigDecimal(0.015)));
        ItemDeCatalogoDeDispositivos disp20 = (new ItemDeCatalogoDeDispositivos("Lampara de 20w",
                true, true, new BigDecimal(0.02)));

        ItemDeCatalogoDeDispositivos disp21 = (new ItemDeCatalogoDeDispositivos(
                "PC de escritorio", true, true, new BigDecimal(0.4)));

        ItemDeCatalogoDeDispositivos disp22 = (new ItemDeCatalogoDeDispositivos(
                "Microondas convencional", false, true, new BigDecimal(0.64)));

        ItemDeCatalogoDeDispositivos disp23 = (new ItemDeCatalogoDeDispositivos(
                "Plancha a vapor", false, true, new BigDecimal(0.75)));

        catalogoDispositivosJpaRepository.save(disp1);
        catalogoDispositivosJpaRepository.save(disp2);
        catalogoDispositivosJpaRepository.save(disp3);
        catalogoDispositivosJpaRepository.save(disp4);
        catalogoDispositivosJpaRepository.save(disp5);
        catalogoDispositivosJpaRepository.save(disp6);
        catalogoDispositivosJpaRepository.save(disp7);
        catalogoDispositivosJpaRepository.save(disp8);
        catalogoDispositivosJpaRepository.save(disp9);
        catalogoDispositivosJpaRepository.save(disp10);
        catalogoDispositivosJpaRepository.save(disp11);
        catalogoDispositivosJpaRepository.save(disp12);
        catalogoDispositivosJpaRepository.save(disp13);
        catalogoDispositivosJpaRepository.save(disp14);
        catalogoDispositivosJpaRepository.save(disp15);
        catalogoDispositivosJpaRepository.save(disp16);
        catalogoDispositivosJpaRepository.save(disp17);
        catalogoDispositivosJpaRepository.save(disp18);
        catalogoDispositivosJpaRepository.save(disp19);
        catalogoDispositivosJpaRepository.save(disp20);
        catalogoDispositivosJpaRepository.save(disp21);
        catalogoDispositivosJpaRepository.save(disp22);
        catalogoDispositivosJpaRepository.save(disp23);


        ZonaGeografica zonaGeografica = new ZonaGeografica("asd", 1.0, 1.0, 1.0);
        Transformador transformador = new Transformador(zonaGeografica, -34.612985, -58.470673);

        ClienteResidencial clienteResidencial = new ClienteResidencial("Martin",
                "Saposnic", "gaona", "martinsapo", "123",
                new Documento("40136136", TipoDeDocumento.DNI), "123123123", new Categoria(100.0));
        Hogar hogar = new Hogar(clienteResidencial, 1.0, 1.0, transformador);

        DispositivoInteligente dispositivoInteligente = new DispositivoInteligente(
                "nombre", new BigDecimal(12.0), clienteResidencial, disp23);

        dispositivoInteligente.agregarRegistroDeCambioDeEstadoPersonalizado(
                LocalDateTime.of(2018, 11, 12, 0, 0), EstadoDeDispositivo.PRENDIDO);
        dispositivoInteligente.agregarRegistroDeCambioDeEstadoPersonalizado(
                LocalDateTime.of(2018, 11, 13, 0, 0), EstadoDeDispositivo.APAGADO);
        dispositivoInteligente.agregarRegistroDeCambioDeEstadoPersonalizado(
                LocalDateTime.of(2018, 11, 14, 0, 0), EstadoDeDispositivo.PRENDIDO);

        Sensor sensor = new SensorDeValores(dispositivoInteligente,
                "sensor de temperatura");

        Accion accion = new Accion(AccionesPosibles.PRENDER);
        CondicionPorValor condicion = new CondicionPorValor(1.0, Operador.MAYOR);

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
