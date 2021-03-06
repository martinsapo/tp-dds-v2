package com.utn.dds.tpdds;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("prod")
public class PruebaDeAceptacion7Y8Test {

    @Autowired
    private MockMvc mockMvc;

    public PruebaDeAceptacion7Y8Test() {
    }

    @Test
    public void inicioDeSesionConClienteInexistente() throws Exception {
        this.mockMvc.perform(post("/loginCliente/submit").param("username", "asdasdasdasd")
                .param("password", "werwerwer"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Usuario o contraseña incorrecta")));
    }

    @Test
    public void inicioDeSesionConClienteExistente() throws Exception {
        this.mockMvc.perform(post("/loginCliente/submit").param("username", "martinsapo")
                .param("password", "123"))
                .andExpect(redirectedUrl("/cliente")).andExpect(status().isFound());
    }
}