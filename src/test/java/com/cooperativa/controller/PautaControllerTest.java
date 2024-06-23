package com.cooperativa.controller;

import static org.mockito.Mockito.when;

import com.cooperativa.dto.PautaDTO;
import com.cooperativa.model.Pauta;
import com.cooperativa.service.PautaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {PautaController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class PautaControllerTest {
    @Autowired
    private PautaController pautaController;

    @MockBean
    private PautaService pautaService;


    @Test
    void testCriarPauta() throws Exception {
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setTitulo("Titulo");
        when(pautaService.criarPauta(Mockito.<PautaDTO>any())).thenReturn(pauta);

        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setTitulo("Titulo");
        String content = (new ObjectMapper()).writeValueAsString(pautaDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/pautas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(pautaController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":1,\"titulo\":\"Titulo\"}"));
    }
}
