package com.utn.dds.tpdds.controllers.Cliente;

import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.repository.ClienteResidencialJpaRepository;
import com.utn.model.*;
import com.utn.model.DTOs.MedicionDTO;
import com.utn.model.DTOs.ReglaDTO;
import com.utn.repositorio.RepositorioDeClientesResidenciales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
public class HogarController {

    @Autowired ClienteResidencialJpaRepository clienteResidencialJPARepository;

    @RequestMapping(value = "/hogarState", method = RequestMethod.GET)
    public ModelAndView submithogar(HttpServletRequest request) {
        Integer clienteId = ((ClienteResidencial) request.getSession().getAttribute("cliente")).getId();
        Optional<ClienteResidencial> clienteResidencial =   clienteResidencialJPARepository.findById(clienteId);
        List<ReglaDTO> reglas = clienteResidencial.obtenerReglasDeTodosLosDispositivos();
        clienteResidencial.cargarRegistrosDeCambioDeEstadoEnDispositivos();

        BigDecimal consumos = clienteResidencial.obtenerTodosLosConsumosDeTodosLosDispositivosEnElUltimoMes();

        List<MedicionDTO> mediciones = clienteResidencial.obtenerTodasLasMedicionesDeTodosLosDispositivos();

        ModelMap model = new ModelMap();
        model.addAttribute("dispositivos", clienteResidencial.getDispositivosInteligentes());
        model.addAttribute("reglas", reglas);
        model.addAttribute("consumo", consumos);
        model.addAttribute("mediciones", mediciones);
        return new ModelAndView("hogar", model);
    }
}
