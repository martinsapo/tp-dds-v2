package com.utn.dds.tpdds.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdministratorController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "administrador";
    }

    /*@RequestMapping(value = "/alta", method = RequestMethod.POST)
    public String alta(@ModelAttribute("userFormData") LoginDTO formData, BindingResult result, Model model) {
        return "altaDispositivo";
    }
    @RequestMapping(value = "/baja", method = RequestMethod.POST)
    public String baja(@ModelAttribute("userFormData") LoginDTO formData, BindingResult result, Model model) {
        return "bajaDispositivo";
    }

    @RequestMapping(value = "/modificacion", method = RequestMethod.POST)
    public String goToModificacion(@ModelAttribute("userFormData") LoginDTO formData, BindingResult result, Model model) {
        return "modificacionDispositivo";
    }


    @RequestMapping(value = "/consumos", method = RequestMethod.POST)
    public String goToConsumos(@ModelAttribute("userFormData") LoginDTO formData, BindingResult result, Model model) {
        List<Transformador> transformadores = new ArrayList<>();

        List<ZonaGeografica> zonasGeograficas = RepositorioDeZonasGeograficas.getAllZonasGeograficas();

        for (ZonaGeografica zonaGeografica : zonasGeograficas) {
            for (Transformador transformador : zonaGeografica.getTransformadores()) {
                transformador.cargarDispositivosDeBaseDeDatos();
            }
        }

        model.addAttribute("zonasGeograficas", zonasGeograficas);
        return "hogaresYConsumos";
    }

    @RequestMapping(value = "/reportes", method = RequestMethod.POST)
    public String goToReportes(@ModelAttribute("userFormData") LoginDTO formData, BindingResult result, Model model) {
        return "reportes";
    }

    @RequestMapping(value = "/consumoPorHogar", method = RequestMethod.POST)
    public String goToReporteConsumoPorHogar(@ModelAttribute("userFormData") LoginDTO formData, BindingResult result, Model model, HttpServletRequest request) {
        List<ZonaGeografica> zonasGeograficas = RepositorioDeZonasGeograficas.getAllZonasGeograficas();

        for (ZonaGeografica zonaGeografica : zonasGeograficas) {
            for (Transformador transformador : zonaGeografica.getTransformadores()) {
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
        return "reportes";
    }

    @RequestMapping(value = "/consumoPromedioPorTipo", method = RequestMethod.POST)
    public String goToReporteConsumoPromedioPorTipo(@ModelAttribute("userFormData") LoginDTO formData, BindingResult result, Model model, HttpServletRequest request) {
        List<ZonaGeografica> zonasGeograficas = RepositorioDeZonasGeograficas.getAllZonasGeograficas();

        List<Hogar> hogares = obtenerHogares(zonasGeograficas);
        List<ClienteResidencial> clientes = new ArrayList<ClienteResidencial>();

        for(Hogar hogar : hogares) {
            clientes.add(hogar.getCliente());
        }

        List<DispositivoEstandar> dispositivoEstandars = new ArrayList<DispositivoEstandar>();
        List<DispositivoInteligente> dispositivoInteligentes = new ArrayList<DispositivoInteligente>();

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

        if(dispositivoEstandars != null && dispositivoEstandars.size() > 0) {
            consumoPromedioEstandar = consumoEstandar / dispositivoEstandars.size();
        }

        if(dispositivoInteligentes != null && dispositivoInteligentes.size() > 0) {
            consumoPromedioInteligente = consumoInteligente / dispositivoInteligentes.size();
        }

        model.addAttribute("consumoPromedioEstandar",consumoPromedioEstandar);
        model.addAttribute("consumoPromedioInteligente",consumoPromedioInteligente);
        return "reportes";
    }

    @RequestMapping(value = "/consumoPorTransformador", method = RequestMethod.POST)
    public String goToReporteConsumoPorTransformador(@ModelAttribute("userFormData") LoginDTO formData, BindingResult result, Model model, HttpServletRequest request) {
        List<ZonaGeografica> zonasGeograficas = RepositorioDeZonasGeograficas.getAllZonasGeograficas();

        List<Transformador> transformadores = new ArrayList<Transformador>();

        for (ZonaGeografica zonaGeografica : zonasGeograficas) {
            for (Transformador transformador : zonaGeografica.getTransformadores()) {
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
        return "reportes";
    }

    private List<Hogar> obtenerHogares(List<ZonaGeografica> zonasGeograficas) {
        List<Hogar> hogares = new ArrayList<Hogar>();

        for (ZonaGeografica zonaGeografica : zonasGeograficas) {
            for (Transformador transformador : zonaGeografica.getTransformadores()) {
                transformador.cargarDispositivosDeBaseDeDatos();
                for (Hogar hogar : transformador.getListaDeHogares()) {
                    hogares.add(hogar);
                }
            }
        }

        return hogares;
    }*/
}
