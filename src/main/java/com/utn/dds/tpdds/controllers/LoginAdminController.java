package com.utn.dds.tpdds.controllers;

import com.utn.dds.tpdds.model.Administrador;
import com.utn.dds.tpdds.model.ClienteResidencial;
import com.utn.dds.tpdds.repository.AdministradorJpaRepository;
import com.utn.dds.tpdds.repository.ClienteResidencialJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/loginAdmin")
public class LoginAdminController {
    @Autowired AdministradorJpaRepository administradorJpaRepository;

    @RequestMapping(value = "")
    public String loginAdmin() {
        return "loginAdmin";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ModelAndView submit(@RequestParam("username") String usuario, @RequestParam("password") String contrasena, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            model.addAttribute("errorMessage", "Indique Usuario o contraseña");
            return new ModelAndView("/loginAdmin", model);
        }

        Administrador administrador = administradorJpaRepository.findAdministradorByNombreDeUsuario(usuario);

        if (administrador == null || !administrador.passwordMatch(contrasena)) {
            model.addAttribute("errorMessage", "Usuario o contraseña incorrecta");
            return new ModelAndView("/loginAdmin", model);
        }

        request.getSession().setAttribute("administrador", administrador);
        request.getSession().setAttribute("username", administrador.getNombreDeUsuario());
        return new ModelAndView("redirect:/admin");
    }
}
