package com.utn.dds.tpdds.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogoutController {

    @RequestMapping(value = "/logout")
    public String Logout(HttpServletRequest request) {

        request.getSession().invalidate();
        return "loginCliente";
    }

}
