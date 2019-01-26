package com.utn.dds.tpdds.controllers.Administrador;

import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.model.DispositivoEstandar;
import com.utn.dds.tpdds.model.DispositivoInteligente;
import com.utn.dds.tpdds.model.Hogar;
import com.utn.dds.tpdds.model.Transformador;
import com.utn.dds.tpdds.model.ZonaGeografica;
import com.utn.dds.tpdds.model.reportes.ReporteConsumoPromedioPorTipoDeDispositivo;
import com.utn.dds.tpdds.model.reportes.ReporteDeConsumoTotalPorHogar;
import com.utn.dds.tpdds.repository.ReporteDeConsumoPromedioPorTipoDeDispositivoMongoRepository;
import com.utn.dds.tpdds.repository.ReporteDeConsumoTotalPorHogarMongoRepository;
import com.utn.dds.tpdds.repository.ZonaGeograficaJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin")
public class AdministratorController {

    @Autowired ZonaGeograficaJpaRepository zonaGeograficaJpaRepository;
    @Autowired ReporteDeConsumoTotalPorHogarMongoRepository reporteDeConsumoTotalPorHogarMongoRepository;
    @Autowired ReporteDeConsumoPromedioPorTipoDeDispositivoMongoRepository reporteDeConsumoPromedioPorTipoDeDispositivoMongoRepository;

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

        String inicio = request.getParameter("fechaInicioHogar") + " 00:00";
        String fin = request.getParameter("fechaFinHogar") + " 23:59";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime fechaInicio = LocalDateTime.parse(inicio, formatter);
        LocalDateTime fechaFin = LocalDateTime.parse(fin, formatter);

        String id = "reporte_de_consumo_total_por_hogar_" + fechaInicio.toString() + "_" + fechaFin.toString();

        Optional<ReporteConsumoPromedioPorTipoDeDispositivo> optionalReporteDeConsumoTotalPorHogar = reporteDeConsumoPromedioPorTipoDeDispositivoMongoRepository.findById(id);

        if (optionalReporteDeConsumoTotalPorHogar.isPresent()) {
            model.addAttribute("reporteDeConsumoTotalPorHogar", optionalReporteDeConsumoTotalPorHogar.get());
        } else {
            List<ZonaGeografica> zonasGeograficas = zonaGeograficaJpaRepository.findAll();
            ReporteDeConsumoTotalPorHogar reporteDeConsumoTotalPorHogar = new ReporteDeConsumoTotalPorHogar(zonasGeograficas, fechaInicio, fechaFin);
            model.addAttribute("reporteDeConsumoTotalPorHogar", reporteDeConsumoTotalPorHogar);
            reporteDeConsumoTotalPorHogarMongoRepository.save(reporteDeConsumoTotalPorHogar);
        }


        return new ModelAndView("reportes", model);
    }

    @RequestMapping(value = "/reportes/consumoPromedioPorTipo", method = RequestMethod.GET)
    public ModelAndView goToReporteConsumoPromedioPorTipo(HttpServletRequest request) {
        ModelMap model = new ModelMap();

        String inicio = request.getParameter("fechaInicioPromedio") + " 00:00";
        String fin = request.getParameter("fechaFinPromedio") + " 23:59";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime fechaInicio = LocalDateTime.parse(inicio, formatter);
        LocalDateTime fechaFin = LocalDateTime.parse(fin, formatter);


        String id = "reporte_de_consumo_promedio_por_tipo_de_dispositivo" + fechaInicio.toString() + "_" + fechaFin.toString();

        Optional<ReporteConsumoPromedioPorTipoDeDispositivo> optionalReporteConsumoPromedioPorTipoDeDispositivo = reporteDeConsumoPromedioPorTipoDeDispositivoMongoRepository.findById(id);

        if (optionalReporteConsumoPromedioPorTipoDeDispositivo.isPresent()) {
            model.addAttribute("reporteConsumoPromedioPorTipoDeDispositivo", optionalReporteConsumoPromedioPorTipoDeDispositivo.get());
        } else {
            List<ZonaGeografica> zonasGeograficas = zonaGeograficaJpaRepository.findAll();
            ReporteConsumoPromedioPorTipoDeDispositivo reporteConsumoPromedioPorTipoDeDispositivo = new ReporteConsumoPromedioPorTipoDeDispositivo(zonasGeograficas, fechaInicio, fechaFin);
            model.addAttribute("reporteConsumoPromedioPorTipoDeDispositivo", reporteConsumoPromedioPorTipoDeDispositivo);
            reporteDeConsumoPromedioPorTipoDeDispositivoMongoRepository.save(reporteConsumoPromedioPorTipoDeDispositivo);
        }

        return new ModelAndView("reportes", model);
    }

    @RequestMapping(value = "/reportes/consumoPorTransformador", method = RequestMethod.GET)
    public ModelAndView goToReporteConsumoPorTransformador( HttpServletRequest request) {
        ModelMap model = new ModelMap();
        List<ZonaGeografica> zonasGeograficas = zonaGeograficaJpaRepository.findAll();

        List<Transformador> transformadores = new ArrayList<>();

        for (ZonaGeografica zonaGeografica : zonasGeograficas) {
            transformadores.addAll(zonaGeografica.getListaDeTransformadores());
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
}
