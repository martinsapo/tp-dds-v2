package com.utn.dds.tpdds;

import com.utn.dds.tpdds.model.Accion;
import com.utn.dds.tpdds.model.AccionesPosibles;
import com.utn.dds.tpdds.model.Administrador;
import com.utn.dds.tpdds.model.Categoria;
import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.model.CondicionDeUsoMensual;
import com.utn.dds.tpdds.model.DispositivoInteligente;
import com.utn.dds.tpdds.model.Documento;
import com.utn.dds.tpdds.model.EstadoDeDispositivo;
import com.utn.dds.tpdds.model.Helper;
import com.utn.dds.tpdds.model.Hogar;
import com.utn.dds.tpdds.model.ItemDeCatalogoDeDispositivos;
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
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@ActiveProfiles("dev")
public class aaaaaFillDataTestDeMartin {
    @Autowired ClienteResidencialJpaRepository clienteResidencialJPARepository;
    @Autowired CatalogoDispositivosJpaRepository catalogoDispositivosJpaRepository;
    @Autowired AdministradorJpaRepository administradorJpaRepository;

    @Test
    public void testFillData() {
        String jsonTablaDeDispositivos = "src/test/java/json/tablaDispositivos.json";
        persistirTablaDispositivos(jsonTablaDeDispositivos);

        ItemDeCatalogoDeDispositivos disp2 = catalogoDispositivosJpaRepository.findDispositivoDeCatalogoById(3);
        ItemDeCatalogoDeDispositivos disp7 = catalogoDispositivosJpaRepository.findDispositivoDeCatalogoById(8);
        ItemDeCatalogoDeDispositivos disp9 = catalogoDispositivosJpaRepository.findDispositivoDeCatalogoById(10);


        ZonaGeografica zonaGeografica = new ZonaGeografica("Mitre", 1.0, 1.0, 1.0);
        Transformador transformador = new Transformador(zonaGeografica, -34.612985, -58.470673);

        ClienteResidencial clienteResidencial = new ClienteResidencial("Martin","Saposnic", "gaona", "martinsapo", "123",
                new Documento("40136136", TipoDeDocumento.DNI), "123123123", new Categoria(100.0));

        clienteResidencial.setFechaDeAlta(new DateTime());

        Hogar hogar = new Hogar(clienteResidencial, 3.0, 2.0, transformador);


        DispositivoInteligente tv = new DispositivoInteligente("TV tubo", clienteResidencial, disp2);
        DispositivoInteligente heladera = new DispositivoInteligente("Heladera", clienteResidencial,disp9);
        DispositivoInteligente smarttv = new DispositivoInteligente("Tv LCD”",clienteResidencial, disp7);


        tv.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 1, 20, 0, 0), EstadoDeDispositivo.PRENDIDO);
        tv.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 1, 20, 12, 0), EstadoDeDispositivo.APAGADO);
        tv.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 1, 21, 0, 0), EstadoDeDispositivo.PRENDIDO);
        tv.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 1, 23, 14, 0), EstadoDeDispositivo.APAGADO);

        heladera.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 1, 21, 0, 0), EstadoDeDispositivo.PRENDIDO);
        heladera.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 1, 22, 12, 0), EstadoDeDispositivo.APAGADO);
        heladera.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 1, 24, 0, 0), EstadoDeDispositivo.PRENDIDO);
        heladera.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 1, 25, 0, 0), EstadoDeDispositivo.APAGADO);

        smarttv.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 1, 26, 0, 0), EstadoDeDispositivo.PRENDIDO);
        smarttv.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 1, 27, 0, 0), EstadoDeDispositivo.APAGADO);

        clienteResidencial.addDispositivo(tv);
        clienteResidencial.addDispositivo(heladera);
        clienteResidencial.addDispositivo(smarttv);

        clienteResidencialJPARepository.save(clienteResidencial);

        Administrador administrador = new Administrador();
        administrador.setPassword("123");
        administrador.setNombreDeUsuario("martinsapo");

        administradorJpaRepository.save(administrador);

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
