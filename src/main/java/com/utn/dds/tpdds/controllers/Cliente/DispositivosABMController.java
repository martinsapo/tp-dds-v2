package com.utn.dds.tpdds.controllers.Cliente;

import com.utn.dds.tpdds.repository.ClienteResidencialJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/abmDispositivos")
public class DispositivosABMController {

    @Autowired ClienteResidencialJpaRepository clienteResidencialJPARepository;

    @RequestMapping(value = "")
    public String abmDispositivos() {
        return "dispositivosABM";
    }


    /*List<CatalogoDispositivos> dispositivosDelCatalogo =  RepositorioDeDispositivosDeCatalogo.obtenerDispositivosDeCatalogo();

    @RequestMapping(value = "/dispositivosABM", method = RequestMethod.GET)
    public ModelAndView submitDispositivoABM(@ModelAttribute("userFormData") LoginDTO formData,  Model model, HttpServletRequest request) {

        String username = request.getSession().getAttribute("username").toString();

        model.addAttribute("dispositivosDelCatalogo", dispositivosDelCatalogo);
        model.addAttribute("username",username);
        return new ModelAndView("dispositivosABM", null);
    }

    @RequestMapping(value = "/asociarDispositivo", method = RequestMethod.GET)
    public String asociarDispositivo(HttpServletRequest request, @RequestParam("catalogo") String catalogoIdElegido,Model model) {

        String username = request.getSession().getAttribute("username").toString();
        ClienteResidencial cliente = c.buscarPorUsername(username);
        List<Dispositivo> dispositivosDelCliente = cliente.getDispositivos();

        CatalogoDispositivos catalogoElegido = RepositorioDeDispositivosDeCatalogo.buscarPorId(Integer.parseInt(catalogoIdElegido));

        for (Dispositivo dispositivo : dispositivosDelCliente){
            if(dispositivo.getNombreDelDispositivo().equals(catalogoElegido.getNombre())){
                model.addAttribute("msg", "El item de catalogo seleccionado ya existe");
                model.addAttribute("dispositivosDelCatalogo", dispositivosDelCatalogo);
                return "dispositivosABM";
            }
        }
        if(catalogoElegido.getEsInteligente()){
            DispositivoInteligente nuevoDispositivoInteligente = new DispositivoInteligente(catalogoElegido.getNombre(), catalogoElegido.getConsumo(), cliente);
            RepositorioDeDispositivosInteligentes.persistir(nuevoDispositivoInteligente);
            model.addAttribute("msg", "Dispositivo Inteligente Creado");

        }
        else{

            DispositivoEstandar nuevoDispositivoEstandar = new DispositivoEstandar(catalogoElegido.getNombre(), catalogoElegido.getConsumo(), cliente,2);
            RepositorioDeDispositivosEstandar.persistir(nuevoDispositivoEstandar);
            model.addAttribute("msg", "Dispositivo Estandar Creado");
        }

        return "dispositivosABM";
    }*/
}