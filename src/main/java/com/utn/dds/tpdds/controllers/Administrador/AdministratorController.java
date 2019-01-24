package com.utn.dds.tpdds.controllers.Administrador;

import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.model.DispositivoEstandar;
import com.utn.dds.tpdds.model.DispositivoInteligente;
import com.utn.dds.tpdds.model.Hogar;
import com.utn.dds.tpdds.model.Transformador;
import com.utn.dds.tpdds.model.ZonaGeografica;
import com.utn.dds.tpdds.repository.ZonaGeograficaJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdministratorController {

    @Autowired ZonaGeograficaJpaRepository zonaGeograficaJpaRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "administrador";
    }


    @RequestMapping(value = "/verHogaresYConsumos", method = RequestMethod.GET)
    public ModelAndView verHogaresYConsumos() {
        ModelMap model = new ModelMap();

        List<ZonaGeografica> zonasGeograficas = zonaGeograficaJpaRepository.findAll();
        List<Hogar> todosLosHogares = new ArrayList<>();
        for (ZonaGeografica zonaGeografica : zonasGeograficas) {
            todosLosHogares.addAll(zonaGeografica.getAllHogares());
        }

        model.addAttribute("hogares", todosLosHogares);
        return new ModelAndView("hogaresYConsumos", model);
    }

    @RequestMapping(value = "/reportes", method = RequestMethod.GET)
    public String goToReportes() {
        return "reportes";
    }

    @RequestMapping(value = "/reportes/consumoPorHogar", method = RequestMethod.GET)
    public ModelAndView goToReporteConsumoPorHogar(HttpServletRequest request) {
        ModelMap model = new ModelMap();

        List<ZonaGeografica> zonasGeograficas = zonaGeograficaJpaRepository.findAll();

        for (ZonaGeografica zonaGeografica : zonasGeograficas) {
            for (Transformador transformador : zonaGeografica.getListaDeTransformadores()) {
                transformador.cargarDispositivosDeBaseDeDatos();
            }
        }

        String inicio = request.getParameter("fechaInicioHogar") + " 00:00";
        String fin = request.getParameter("fechaFinHogar") + " 23:59";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime fechaInicio = LocalDateTime.parse(inicio, formatter);
        LocalDateTime fechaFin = LocalDateTime.parse(fin, formatter);

        List<Hogar> hogares = obtenerHogares(zonasGeograficas);

        model.addAttribute("hogares", hogares);
        model.addAttribute("startDate", fechaInicio);
        model.addAttribute("endDate", fechaFin);
        return new ModelAndView("reportes", model);
    }

    @RequestMapping(value = "/reportes/consumoPromedioPorTipo", method = RequestMethod.GET)
    public ModelAndView goToReporteConsumoPromedioPorTipo(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        List<ZonaGeografica> zonasGeograficas = zonaGeograficaJpaRepository.findAll();

        List<Hogar> hogares = obtenerHogares(zonasGeograficas);
        List<ClienteResidencial> clientes = new ArrayList<>();

        for(Hogar hogar : hogares) {
            clientes.add(hogar.getCliente());
        }

        List<DispositivoEstandar> dispositivoEstandars = new ArrayList<>();
        List<DispositivoInteligente> dispositivoInteligentes = new ArrayList<>();

        for (ClienteResidencial cliente : clientes) {
            dispositivoEstandars.addAll(cliente.getDispositivosEstandars());
            dispositivoInteligentes.addAll(cliente.getDispositivosInteligentes());
        }

        double consumoEstandar = 0;
        double consumoInteligente = 0;

        String inicio = request.getParameter("fechaInicioPromedio") + " 00:00";
        String fin = request.getParameter("fechaFinPromedio") + " 23:59";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime fechaInicio = LocalDateTime.parse(inicio, formatter);
        LocalDateTime fechaFin = LocalDateTime.parse(fin, formatter);

        for (DispositivoEstandar dispositivoEstandar : dispositivoEstandars) {
            consumoEstandar = consumoEstandar + dispositivoEstandar.cantidadDeEnergiaConsumidaEnUnPeriodo(fechaInicio, fechaFin).doubleValue();
        }

        for (DispositivoInteligente dispositivoInteligente : dispositivoInteligentes) {
            consumoInteligente = consumoInteligente + dispositivoInteligente.cantidadDeEnergiaConsumidaEnUnPeriodo(fechaInicio, fechaFin).doubleValue();
        }

        double consumoPromedioEstandar = 0;
        double consumoPromedioInteligente = 0;

        if(dispositivoEstandars.size() > 0) {
            consumoPromedioEstandar = consumoEstandar / dispositivoEstandars.size();
        }

        if(dispositivoInteligentes.size() > 0) {
            consumoPromedioInteligente = consumoInteligente / dispositivoInteligentes.size();
        }

        model.addAttribute("consumoPromedioEstandar",consumoPromedioEstandar);
        model.addAttribute("consumoPromedioInteligente",consumoPromedioInteligente);
        return new ModelAndView("reportes", model);
    }

    @RequestMapping(value = "/reportes/consumoPorTransformador", method = RequestMethod.GET)
    public ModelAndView goToReporteConsumoPorTransformador( HttpServletRequest request) {
        ModelMap model = new ModelMap();
        List<ZonaGeografica> zonasGeograficas = zonaGeograficaJpaRepository.findAll();

        List<Transformador> transformadores = new ArrayList<>();

        for (ZonaGeografica zonaGeografica : zonasGeograficas) {
            for (Transformador transformador : zonaGeografica.getListaDeTransformadores()) {
                transformador.cargarDispositivosDeBaseDeDatos();
                transformadores.add(transformador);
            }
        }

        String inicio = request.getParameter("fechaInicioTransformador") + " 00:00";
        String fin = request.getParameter("fechaFinTransformador") + " 23:59";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime fechaInicio = LocalDateTime.parse(inicio, formatter);
        LocalDateTime fechaFin = LocalDateTime.parse(fin, formatter);

        model.addAttribute("startTime",fechaInicio);
        model.addAttribute("endTime",fechaFin);
        model.addAttribute("transformadores",transformadores);
        return new ModelAndView("reportes", model);
    }

    private List<Hogar> obtenerHogares(List<ZonaGeografica> zonasGeograficas) {
        List<Hogar> hogares = new ArrayList<>();

        for (ZonaGeografica zonaGeografica : zonasGeograficas) {
            for (Transformador transformador : zonaGeografica.getListaDeTransformadores()) {
                transformador.cargarDispositivosDeBaseDeDatos();
                hogares.addAll(transformador.getListaDeHogares());
            }
        }

        return hogares;
    }
}
