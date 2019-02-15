package com.utn.dds.tpdds.controllers.Cliente;

import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.model.DispositivoEstandar;
import com.utn.dds.tpdds.model.DispositivoInteligente;
import com.utn.dds.tpdds.model.EstadoDeDispositivo;
import com.utn.dds.tpdds.model.ItemDeCatalogoDeDispositivos;
import com.utn.dds.tpdds.repository.CatalogoDispositivosJpaRepository;
import com.utn.dds.tpdds.repository.ClienteResidencialJpaRepository;
import com.utn.dds.tpdds.repository.DispositivoEstandarJpaRepository;
import com.utn.dds.tpdds.repository.DispositivoInteligenteJpaRepository;
import com.utn.dds.tpdds.repository.DispositivoJpaRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "cliente/abmDispositivos")
public class DispositivosABMController {

    @Autowired
    ClienteResidencialJpaRepository clienteResidencialJPARepository;
    @Autowired
    DispositivoInteligenteJpaRepository dispositivoInteligenteJpaRepository;
    @Autowired
    DispositivoEstandarJpaRepository dispositivoEstandarJpaRepository;
    @Autowired
    DispositivoJpaRepository dispositivoJpaRepository;
    @Autowired
    CatalogoDispositivosJpaRepository catalogoDispositivosJpaRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        ClienteResidencial cliente = ((ClienteResidencial) request.getSession().getAttribute("cliente"));
        List<DispositivoInteligente> dispositivosInteligentesDelCliente = dispositivoInteligenteJpaRepository.findDispositivoInteligentesByDueno(cliente);
        List<DispositivoEstandar> dispositivosEstandarDelCliente = dispositivoEstandarJpaRepository.findDispositivoEstandarByDueno(cliente);
        dispositivosInteligentesDelCliente.forEach(DispositivoInteligente::actualizarState);
        model.addAttribute("dispositivosInteligentes", dispositivosInteligentesDelCliente);
        model.addAttribute("dispositivosEstandar", dispositivosEstandarDelCliente);
        return new ModelAndView("dispositivosABM", model);
    }

    @RequestMapping(value = "/encender", method = RequestMethod.GET)
    public ModelAndView encender(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));

        Optional<DispositivoInteligente> optionalDispositivoInteligente = dispositivoInteligenteJpaRepository.findById(id);

        if (optionalDispositivoInteligente.isPresent()) {
            DispositivoInteligente dispositivoInteligente = optionalDispositivoInteligente.get();
            dispositivoInteligente.encender();
            dispositivoInteligenteJpaRepository.save(dispositivoInteligente);
        }

        return new ModelAndView("redirect:/cliente/abmDispositivos");
    }

    @RequestMapping(value = "/apagar", method = RequestMethod.GET)
    public ModelAndView apagar(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));

        Optional<DispositivoInteligente> optionalDispositivoInteligente = dispositivoInteligenteJpaRepository.findById(id);

        if (optionalDispositivoInteligente.isPresent()) {
            DispositivoInteligente dispositivoInteligente = optionalDispositivoInteligente.get();
            dispositivoInteligente.apagar();
            dispositivoInteligenteJpaRepository.save(dispositivoInteligente);
        }

        return new ModelAndView("redirect:/cliente/abmDispositivos");
    }

    @RequestMapping(value = "/modoAhorro", method = RequestMethod.GET)
    public ModelAndView modoAhorro(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));

        Optional<DispositivoInteligente> optionalDispositivoInteligente = dispositivoInteligenteJpaRepository.findById(id);

        if (optionalDispositivoInteligente.isPresent()) {
            DispositivoInteligente dispositivoInteligente = optionalDispositivoInteligente.get();
            dispositivoInteligente.cambiarAAhorroDeEnergia();
            dispositivoInteligenteJpaRepository.save(dispositivoInteligente);
        }

        return new ModelAndView("redirect:/cliente/abmDispositivos");
    }

    @RequestMapping(value = "/altaInteligente", method = RequestMethod.GET)
    public ModelAndView altaInteligente() {
        ModelMap model = new ModelMap();
        List<ItemDeCatalogoDeDispositivos> items = catalogoDispositivosJpaRepository.findAll();
        List<ItemDeCatalogoDeDispositivos> itemsInteligentes = new ArrayList<>();
        for (ItemDeCatalogoDeDispositivos item : items) {
            if (item.getEsInteligente()) {
                itemsInteligentes.add(item);
            }
        }
        model.addAttribute("catalogo", itemsInteligentes);
        return new ModelAndView("dispositivosInteligentesAlta", model);
    }

    @RequestMapping(value = "/altaEstandar", method = RequestMethod.GET)
    public ModelAndView altaEstandar() {
        ModelMap model = new ModelMap();
        List<ItemDeCatalogoDeDispositivos> items = catalogoDispositivosJpaRepository.findAll();
        List<ItemDeCatalogoDeDispositivos> itemsEstandar = new ArrayList<>();
        for (ItemDeCatalogoDeDispositivos item : items) {
            if (!item.getEsInteligente()) {
                itemsEstandar.add(item);
            }
        }
        model.addAttribute("catalogo", itemsEstandar);
        return new ModelAndView("dispositivosEstandarAlta", model);
    }

    @RequestMapping(value = "/altaDesdeArchivo", method = RequestMethod.GET)
    public String altaDesdeArchivo() {
        return "cargaDeDispositivoDesdeArchivo";
    }

    @RequestMapping(value = "/altaDesdeArchivo/submit", method = RequestMethod.POST)
    public ModelAndView altaDesdeArchivoSubmit(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Seleccione un archivo a cargar");
            return new ModelAndView("redirect:/cliente/altaDesdeArchivo");
        }

        try {
            String content = new String(file.getBytes(), "UTF-8");
            JSONObject jsonObj = new JSONObject(content);
            if (jsonObj.has("inteligente")) {
                JSONObject map = (JSONObject) jsonObj.get("inteligente");
                Integer clienteId = (Integer) map.get("idCliente");
                Optional<ClienteResidencial> optionalClienteResidencial = clienteResidencialJPARepository.findById(clienteId);
                Integer catalogoId = (Integer) map.get("idCatalogo");
                Optional<ItemDeCatalogoDeDispositivos> optionalItemDeCatalogoDeDispositivos = catalogoDispositivosJpaRepository.findById(catalogoId);
                String nombre = (String) map.get("nombre");

                if (optionalClienteResidencial.isPresent() && optionalItemDeCatalogoDeDispositivos.isPresent()) {
                    DispositivoInteligente dispositivoInteligente = new DispositivoInteligente(nombre, optionalClienteResidencial.get(), optionalItemDeCatalogoDeDispositivos.get());


                    JSONArray registros = map.getJSONArray("registrosDeCambioDeEstado");
                    for (int i = 0; i < registros.length(); i++) {
                        JSONObject registro = (JSONObject) registros.get(i);
                        LocalDateTime date = LocalDateTime.of((Integer) registro.get("ano"), (Integer) registro.get("mes"), (Integer) registro.get("dia"), (Integer) registro.get("hora"), (Integer) registro.get("minuto"));

                        dispositivoInteligente.agregarRegistroDeCambioDeEstadoPersonalizado(date, EstadoDeDispositivo.valueOf(registro.get("estado").toString()));
                    }
                    dispositivoInteligenteJpaRepository.save(dispositivoInteligente);
                } else {
                    ClienteResidencial cliente = ((ClienteResidencial) request.getSession().getAttribute("cliente"));
                    DispositivoInteligente dispositivoInteligente = new DispositivoInteligente(nombre, cliente, optionalItemDeCatalogoDeDispositivos.get());


                    JSONArray registros = map.getJSONArray("registrosDeCambioDeEstado");
                    for (int i = 0; i < registros.length(); i++) {
                        JSONObject registro = (JSONObject) registros.get(i);
                        LocalDateTime date = LocalDateTime.of((Integer) registro.get("ano"), (Integer) registro.get("mes"), (Integer) registro.get("dia"), (Integer) registro.get("hora"), (Integer) registro.get("minuto"));

                        dispositivoInteligente.agregarRegistroDeCambioDeEstadoPersonalizado(date, EstadoDeDispositivo.valueOf(registro.get("estado").toString()));
                    }
                    dispositivoInteligenteJpaRepository.save(dispositivoInteligente);
                }
            }

            if (jsonObj.has("estandar")) {
                Map map = (Map) jsonObj.get("estandar");
                Integer clienteId = (Integer) map.get("idCliente");
                Optional<ClienteResidencial> optionalClienteResidencial = clienteResidencialJPARepository.findById(clienteId);
                Integer catalogoId = (Integer) map.get("idCatalogo");
                Optional<ItemDeCatalogoDeDispositivos> optionalItemDeCatalogoDeDispositivos = catalogoDispositivosJpaRepository.findById(catalogoId);
                String nombre = (String) map.get("nombre");
                Double consumo = (Double) map.get("consumo");

                if (optionalClienteResidencial.isPresent() && optionalItemDeCatalogoDeDispositivos.isPresent()) {
                    DispositivoEstandar dispositivoEstandar = new DispositivoEstandar(nombre, optionalClienteResidencial.get(), consumo, optionalItemDeCatalogoDeDispositivos.get());
                    dispositivoEstandarJpaRepository.save(dispositivoEstandar);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("redirect:/cliente/altaDesdeArchivo");
        }

        return new ModelAndView("redirect:/cliente/abmDispositivos");
    }

    @RequestMapping(value = "/altaInteligente/submit", method = RequestMethod.GET)
    public ModelAndView altaInteligenteSubmit(HttpServletRequest request) {
        ClienteResidencial cliente = ((ClienteResidencial) request.getSession().getAttribute("cliente"));
        Integer idCatalogo = Integer.parseInt(request.getParameter("catalogo"));
        String nombre = request.getParameter("nombre");
        ItemDeCatalogoDeDispositivos itemDeCatalogoDeDispositivos = catalogoDispositivosJpaRepository.findById(idCatalogo).get();
        DispositivoInteligente dispositivoInteligente = new DispositivoInteligente(nombre, cliente, itemDeCatalogoDeDispositivos);
        dispositivoInteligenteJpaRepository.save(dispositivoInteligente);
        return new ModelAndView("redirect:/cliente/abmDispositivos");
    }

    @RequestMapping(value = "/altaEstandar/submit", method = RequestMethod.GET)
    public ModelAndView altaEstandarSubmit(HttpServletRequest request) {
        ClienteResidencial cliente = ((ClienteResidencial) request.getSession().getAttribute("cliente"));
        Integer idCatalogo = Integer.parseInt(request.getParameter("catalogo"));
        String nombre = request.getParameter("nombre");
        Double usodiario = Double.parseDouble(request.getParameter("usoDiario"));
        ItemDeCatalogoDeDispositivos itemDeCatalogoDeDispositivos = catalogoDispositivosJpaRepository.findById(idCatalogo).get();
        DispositivoEstandar dispositivoEstandar = new DispositivoEstandar(
                nombre, cliente, usodiario, itemDeCatalogoDeDispositivos);
        dispositivoEstandarJpaRepository.save(dispositivoEstandar);
        return new ModelAndView("redirect:/cliente/abmDispositivos");
    }

    @RequestMapping(value = "/baja/submit", method = RequestMethod.GET)
    public ModelAndView bajaSubmit(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        dispositivoJpaRepository.deleteById(id);

        return new ModelAndView("redirect:/cliente/abmDispositivos");
    }
}