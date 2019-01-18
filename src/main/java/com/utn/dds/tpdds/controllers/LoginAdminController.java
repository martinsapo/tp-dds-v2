package com.utn.dds.tpdds.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@SessionAttributes("admin")
public class LoginAdminController {

    /*@RequestMapping(value = "/adminLogin")
    public String LoginAdmin() {
        return "loginAdmin";
    }

    @RequestMapping(value = "/submitLoginAdmin", method = RequestMethod.POST)
    public String submit(@ModelAttribute("userFormData") LoginDTO formData, BindingResult result, Model model, @ModelAttribute("admin") Administrador administrador) {
        if (formData.getUsername() == null || formData.getPassword() == null){
            model.addAttribute("msg","Indique Usuario o contraseña");
            return "loginAdmin";
        }

        administrador = RepositorioDeAdministradores.buscarPorUsername(formData.getUsername());

        if (administrador != null && administrador.passwordMatch(formData.getPassword())){
            model.addAttribute("username",formData.getUsername());
            return "administrador";
        }
        else {
            model.addAttribute("msg","Usuario o contrasena incorrecta");
            return "loginAdmin";
        }
    }

    @RequestMapping(value = "/goToMapFromAdmin", method = RequestMethod.GET)
    public ModelAndView goToMap(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("redirect:/mapa");
    }

    @RequestMapping(value = "/goToClientLogin", method = RequestMethod.GET)
    public ModelAndView goToAdminLogin(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("redirect:/");
    }

    @ModelAttribute("admin")
    public Administrador setAdmin() {
        return new Administrador();
    }*/
}
