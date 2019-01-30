package com.utn.dds.tpdds;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("prod")
public class PruebaDeAceptacion1Test {

    @Autowired
    private MockMvc mockMvc;

    public PruebaDeAceptacion1Test() {
    }

    @Test
    public void inicioDeSesionConUsuarioAdmin() throws Exception {
        this.mockMvc.perform(post("/loginAdmin/submit").param("username", "martinsapo")
                .param("password", "123"))
                .andExpect(redirectedUrl("/admin")).andExpect(status().isFound());
    }
}
