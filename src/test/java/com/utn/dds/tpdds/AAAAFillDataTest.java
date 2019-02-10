package com.utn.dds.tpdds;

import com.utn.dds.tpdds.model.*;
import com.utn.dds.tpdds.repository.AdministradorJpaRepository;
import com.utn.dds.tpdds.repository.CatalogoDispositivosJpaRepository;
import com.utn.dds.tpdds.repository.ClienteResidencialJpaRepository;
import com.utn.dds.tpdds.repository.ReglaJpaRepository;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.RepositoryIdHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@ActiveProfiles("dev")
public class AAAAFillDataTest {

    @Autowired ClienteResidencialJpaRepository clienteResidencialJPARepository;
    @Autowired AdministradorJpaRepository administradorJpaRepository;
    @Autowired CatalogoDispositivosJpaRepository catalogoDispositivosJpaRepository;

    public AAAAFillDataTest() {}

    @Test
    public void testFillData() {

        String jsonTablaDeDispositivos = "src/test/java/json/tablaDispositivos.json";
        persistirTablaDispositivos(jsonTablaDeDispositivos);


        ZonaGeografica zonaGeografica = new ZonaGeografica("Mitre", 1.0, 1.0, 1.0);
        Transformador transformador = new Transformador(zonaGeografica, -34.612985, -58.470673);

        ClienteResidencial clienteResidencial = new ClienteResidencial("Martin","Saposnic", "gaona", "martinsapo", "123",
                new Documento("40136136", TipoDeDocumento.DNI), "123123123", new Categoria(100.0));

        ClienteResidencial otroCliente =  new ClienteResidencial("Eric", "Cano", "san martin", "ecano", "1234",
                new Documento("367282827", TipoDeDocumento.DNI), "1364748373", new Categoria(10.0));


        otroCliente.setFechaDeAlta(new DateTime());

        Hogar hogar = new Hogar(otroCliente, 3.0, 2.0, transformador);
        ItemDeCatalogoDeDispositivos disp2 = catalogoDispositivosJpaRepository.findDispositivoDeCatalogoById(3);
        ItemDeCatalogoDeDispositivos disp7 = catalogoDispositivosJpaRepository.findDispositivoDeCatalogoById(8);
        ItemDeCatalogoDeDispositivos disp9 = catalogoDispositivosJpaRepository.findDispositivoDeCatalogoById(10);

        DispositivoInteligente tv = new DispositivoInteligente("TV tubo", otroCliente, disp2);
        DispositivoInteligente heladera = new DispositivoInteligente("Heladera", otroCliente,disp9);
        DispositivoInteligente smarttv = new DispositivoInteligente("Tv LCD”",otroCliente, disp7);

        agregarConsumosAdispositivo(smarttv);
        agregarConsumosAdispositivo(heladera);

        tv.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 1, 12, 0, 0), EstadoDeDispositivo.PRENDIDO);
        tv.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 12, 5, 0, 0), EstadoDeDispositivo.APAGADO);
        tv.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 1, 14, 0, 0), EstadoDeDispositivo.PRENDIDO);

        Sensor sensor = new SensorDeValores(tv, "sensor de temperatura");
        Sensor sensor2 = new SensorDeValores(heladera, "sensor de presion");

//        Medicion medicion = new MedicionPorValor();
//        ((MedicionPorValor) medicion).setMedicion(1.0);
//        medicion.setSensor(sensor);
//
//        sensor.agregarMedicion(medicion);

        Accion accion = new Accion(AccionesPosibles.APAGAR);
        CondicionDeUsoMensual condicion = new CondicionDeUsoMensual(360, Operador.MAYOR);

        Regla regla = new Regla(condicion, accion, tv, sensor);

        otroCliente.addDispositivo(tv);

        clienteResidencialJPARepository.save(clienteResidencial);
        clienteResidencialJPARepository.save(otroCliente);

        Administrador administrador = new Administrador();
        administrador.setPassword("123");
        administrador.setNombreDeUsuario("martinsapo");


       administradorJpaRepository.save(administrador);

        hogar.obtenerRecomendacion();
        hogar.mostrarRecomendacion();

    }

    //Para agregar consumo a dispositivo inteligente
    private void agregarConsumosAdispositivo(DispositivoInteligente dispositivo){

        System.out.println("----------------------------------------------------------");
        System.out.println("    Consumo del dispositivo: " + dispositivo.getNombreDelDispositivo());
        System.out.println("----------------------------------------------------------");
        LocalDateTime startTime =  LocalDateTime.of(2018,6,11,0,0);
        LocalDateTime endTime =  LocalDateTime.of(2018, 6, 20,0,0);

        //PERIODO 1
        dispositivo.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 6, 11,0,0), EstadoDeDispositivo.APAGADO);
        dispositivo.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 6, 12,0,0), EstadoDeDispositivo.PRENDIDO);
        dispositivo.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 6, 13,0,0), EstadoDeDispositivo.APAGADO);
        dispositivo.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 6, 14,0,0), EstadoDeDispositivo.PRENDIDO);
        dispositivo.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 6, 15,0,0), EstadoDeDispositivo.APAGADO);
        dispositivo.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 6, 16,0,0), EstadoDeDispositivo.PRENDIDO);
        dispositivo.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 6, 17,0,0), EstadoDeDispositivo.APAGADO);
        dispositivo.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 6, 18,0,0), EstadoDeDispositivo.PRENDIDO);

        // Resultado de consumo periodo1
        BigDecimal consumoInteligente = dispositivo.cantidadDeEnergiaConsumidaEnUnPeriodo(startTime, endTime);
        System.out.println("Consumo inicial: " + consumoInteligente);


        //PERIODO 2
        LocalDateTime startTime2 =  LocalDateTime.of(2018,6,20,0,0);
        LocalDateTime endTime2 =  LocalDateTime.of(2018, 6, 30,0,0);

        dispositivo.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 6, 20,0,0), EstadoDeDispositivo.APAGADO);
        dispositivo.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 6, 21,0,0), EstadoDeDispositivo.PRENDIDO);
        dispositivo.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 6, 22,0,0), EstadoDeDispositivo.APAGADO);
        dispositivo.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 6, 23,0,0), EstadoDeDispositivo.PRENDIDO);
        dispositivo.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 6, 24,0,0), EstadoDeDispositivo.APAGADO);
        dispositivo.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 6, 25,0,0), EstadoDeDispositivo.PRENDIDO);
        dispositivo.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 6, 26,0,0), EstadoDeDispositivo.APAGADO);
        dispositivo.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2018, 6, 27,0,0), EstadoDeDispositivo.PRENDIDO);


        // Resultado de consumo periodo2
        consumoInteligente = dispositivo.cantidadDeEnergiaConsumidaEnUnPeriodo(startTime2, endTime2);
        System.out.println("Consumo final: " + consumoInteligente);
        System.out.println("-----------------------------------------");

    }

    private void persistirTablaDispositivos(String jsonCatalogo){

        JSONArray catalogoArray = (JSONArray) Helper.mapJsonToArrayJSON(jsonCatalogo);

        for (Object catalogoDispositivo : catalogoArray) {
            JSONObject object = (JSONObject) catalogoDispositivo;
            ItemDeCatalogoDeDispositivos dispositivoDeCatalogo = new ItemDeCatalogoDeDispositivos(object);
            catalogoDispositivosJpaRepository.save(dispositivoDeCatalogo);

        }
    }



}
