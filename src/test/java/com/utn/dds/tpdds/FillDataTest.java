package com.utn.dds.tpdds;

import com.utn.dds.tpdds.model.Accion;
import com.utn.dds.tpdds.model.AccionesPosibles;
import com.utn.dds.tpdds.model.Administrador;
import com.utn.dds.tpdds.model.CatalogoDispositivos;
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
        ZonaGeografica zonaGeografica = new ZonaGeografica("asd", 1.0, 1.0, 1.0);
        Transformador transformador = new Transformador(zonaGeografica, -34.612985, -58.470673);

        ClienteResidencial clienteResidencial = new ClienteResidencial("Martin",
                "Saposnic", "gaona", "martinsapo", "123",
                new Documento("40136136", TipoDeDocumento.DNI), "123123123", new Categoria(100.0));
        Hogar hogar = new Hogar(clienteResidencial, 1.0, 1.0, transformador);

        DispositivoInteligente dispositivoInteligente = new DispositivoInteligente(
                "nombre", new BigDecimal(12.0), clienteResidencial);

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

        CatalogoDispositivos disp1 = new CatalogoDispositivos(
                "Aire de 2200 frigorias", true, false, new BigDecimal(1.013));

        CatalogoDispositivos disp2 = new CatalogoDispositivos(
                "TV de tubo fluorescente de 21", true, false, new BigDecimal(0.075));
        CatalogoDispositivos disp3 = (new CatalogoDispositivos(
                "TV de tubo fluorescente de 29 a 34", true, false, new BigDecimal(0.175)));
        CatalogoDispositivos disp4 = (new CatalogoDispositivos("TV LCD de 40",
                false, false, new BigDecimal(0.18)));
        CatalogoDispositivos disp5 = (new CatalogoDispositivos("TV LED de 24",
                true, true, new BigDecimal(0.04)));
        CatalogoDispositivos disp6 = (new CatalogoDispositivos("TV LED de 32",
                true, true, new BigDecimal(0.055)));
        CatalogoDispositivos disp7 = (new CatalogoDispositivos("TV LED de 40",
                true, true, new BigDecimal(0.08)));

        CatalogoDispositivos disp8 = (new CatalogoDispositivos(
                "Heladera con freezer", true, true, new BigDecimal(0.09)));
        CatalogoDispositivos disp9 = (new CatalogoDispositivos(
                "Heladera sin freezer", true, true, new BigDecimal(0.075)));

        CatalogoDispositivos disp10 = (new CatalogoDispositivos(
                "Lavarropas automatico de 5kg con calentamiento de agua", false,
                false, new BigDecimal(0.0875)));
        CatalogoDispositivos disp11 = (new CatalogoDispositivos(
                "Lavarropas automatico de 5kg", true, true, new BigDecimal(0.175)));
        CatalogoDispositivos disp12 = (new CatalogoDispositivos(
                "Lavarropas semiautomatico de 5kg", false, true, new BigDecimal(0.1275)));

        CatalogoDispositivos disp13 = (new CatalogoDispositivos(
                "Ventilador de pie", false, true, new BigDecimal(0.09)));
        CatalogoDispositivos disp14 = (new CatalogoDispositivos(
                "Ventilador de techo", true, true, new BigDecimal(0.06)));

        CatalogoDispositivos disp15 = (new CatalogoDispositivos(
                "Lampara Halogena de 40w", true, false, new BigDecimal(0.04)));
        CatalogoDispositivos disp16 = (new CatalogoDispositivos(
                "Lampara Halogena de 60w", true, false, new BigDecimal(0.06)));
        CatalogoDispositivos disp17 = (new CatalogoDispositivos(
                "Lampara Halogena de 100w", true, false, new BigDecimal(0.015)));
        CatalogoDispositivos disp18 = (new CatalogoDispositivos("Lampara de 11w",
                true, true, new BigDecimal(0.011)));
        CatalogoDispositivos disp19 = (new CatalogoDispositivos("Lampara de 15w",
                true, true, new BigDecimal(0.015)));
        CatalogoDispositivos disp20 = (new CatalogoDispositivos("Lampara de 20w",
                true, true, new BigDecimal(0.02)));

        CatalogoDispositivos disp21 = (new CatalogoDispositivos(
                "PC de escritorio", true, true, new BigDecimal(0.4)));

        CatalogoDispositivos disp22 = (new CatalogoDispositivos(
                "Microondas convencional", false, true, new BigDecimal(0.64)));

        CatalogoDispositivos disp23 = (new CatalogoDispositivos(
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
        Administrador administrador = new Administrador();
        administrador.setPassword("123");
        administrador.setNombreDeUsuario("martin");


        administradorJpaRepository.save(administrador);

    }
}
