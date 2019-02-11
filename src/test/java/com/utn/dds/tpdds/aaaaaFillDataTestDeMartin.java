package com.utn.dds.tpdds;

import com.utn.dds.tpdds.model.Administrador;
import com.utn.dds.tpdds.model.Categoria;
import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.model.DispositivoEstandar;
import com.utn.dds.tpdds.model.DispositivoInteligente;
import com.utn.dds.tpdds.model.Documento;
import com.utn.dds.tpdds.model.EstadoDeDispositivo;
import com.utn.dds.tpdds.model.Helper;
import com.utn.dds.tpdds.model.Hogar;
import com.utn.dds.tpdds.model.ItemDeCatalogoDeDispositivos;
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
        persistirTablaDispositivos();

        ZonaGeografica zonaGeografica = new ZonaGeografica("Mitre", 1.0, 1.0, 1.0);
        Transformador transformador = new Transformador(zonaGeografica, -34.612985, -58.470673);

        ClienteResidencial clienteResidencial = new ClienteResidencial("Martin","Saposnic", "gaona", "martinsapo", "123",
                new Documento("40136136", TipoDeDocumento.DNI), "123123123", new Categoria(100.0));

        clienteResidencial.setFechaDeAlta(new DateTime());

        Hogar hogar = new Hogar(clienteResidencial, 3.0, 2.0, transformador);

        clienteResidencialJPARepository.save(clienteResidencial);

        DispositivoInteligente tv = new DispositivoInteligente("TV LED 40", clienteResidencial, catalogoDispositivosJpaRepository.findFirstByNombreLike("TV LED de 40"));
        DispositivoInteligente lampara = new DispositivoInteligente("Lampara de 11w", clienteResidencial,catalogoDispositivosJpaRepository.findFirstByNombreLike("Lampara de 11 W"));
        DispositivoEstandar lavarropas = new DispositivoEstandar("lavarropas Semi-automatico de 5 kg", clienteResidencial, 11.0, catalogoDispositivosJpaRepository.findFirstByNombreLike("Lavarropas 5kg semi-automatico"));
        DispositivoInteligente pc = new DispositivoInteligente("PC de escritorio", clienteResidencial, catalogoDispositivosJpaRepository.findFirstByNombreLike("PC de escritorio"));
        DispositivoInteligente aireAcond = new DispositivoInteligente("Aire Acondicionado de 2200 frigorias", clienteResidencial, catalogoDispositivosJpaRepository.findFirstByNombreLike("Aire acondicionado 2200 frigorias"));
        DispositivoEstandar microondasConvencional = new DispositivoEstandar("Microondas convencional", clienteResidencial, 2.0, catalogoDispositivosJpaRepository.findFirstByNombreLike("Microondas convencional"));
        DispositivoEstandar planchaAVapor = new DispositivoEstandar("Plancha a vapor", clienteResidencial, 1.0, catalogoDispositivosJpaRepository.findFirstByNombreLike("Plancha a vapor"));
        DispositivoInteligente ventiladorDeTecho = new DispositivoInteligente("Ventilador de techo", clienteResidencial, catalogoDispositivosJpaRepository.findFirstByNombreLike("Ventilador de techo"));


        tv.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 2, 5, 0, 0), EstadoDeDispositivo.PRENDIDO);
        tv.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 2, 5, 12, 0), EstadoDeDispositivo.APAGADO);
        tv.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 2, 6, 0, 0), EstadoDeDispositivo.PRENDIDO);
        tv.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 2, 8, 14, 0), EstadoDeDispositivo.APAGADO);

        pc.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 2, 2, 0, 0), EstadoDeDispositivo.PRENDIDO);
        pc.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 2, 3, 12, 0), EstadoDeDispositivo.APAGADO);
        pc.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 2, 4, 0, 0), EstadoDeDispositivo.PRENDIDO);
        pc.agregarRegistroDeCambioDeEstadoPersonalizado(LocalDateTime.of(2019, 2, 5, 0, 0), EstadoDeDispositivo.APAGADO);

        clienteResidencial.addDispositivo(tv);
        clienteResidencial.addDispositivo(lampara);
        clienteResidencial.addDispositivo(lavarropas);
        clienteResidencial.addDispositivo(pc);
        clienteResidencial.addDispositivo(aireAcond);
        clienteResidencial.addDispositivo(microondasConvencional);
        clienteResidencial.addDispositivo(planchaAVapor);
        clienteResidencial.addDispositivo(ventiladorDeTecho);


        clienteResidencialJPARepository.save(clienteResidencial);

        Administrador administrador = new Administrador();
        administrador.setPassword("123");
        administrador.setNombreDeUsuario("martinsapo");

        administradorJpaRepository.save(administrador);

    }

    private void persistirTablaDispositivos(){

        JSONArray catalogoArray = (JSONArray) Helper.mapJsonToArrayJSON("src/test/java/json/tablaDispositivos.json");

        assert catalogoArray != null;
        for (Object catalogoDispositivo : catalogoArray) {
            JSONObject object = (JSONObject) catalogoDispositivo;
            ItemDeCatalogoDeDispositivos dispositivoDeCatalogo = new ItemDeCatalogoDeDispositivos(object);
            catalogoDispositivosJpaRepository.save(dispositivoDeCatalogo);

        }
    }
}
