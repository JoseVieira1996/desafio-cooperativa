package com.cooperativa.controller;

import static org.mockito.Mockito.when;

import com.cooperativa.dto.VotoDTO;
import com.cooperativa.model.Pauta;
import com.cooperativa.model.Sessao;
import com.cooperativa.model.Voto;
import com.cooperativa.service.VotoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;

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

@ContextConfiguration(classes = {VotoController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class VotoControllerTest {
    @Autowired
    private VotoController votoController;

    @MockBean
    private VotoService votoService;


    @Test
    void testVotar() throws Exception {
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setTitulo("Titulo");

        Sessao sessao = new Sessao();
        sessao.setDataFim(LocalDate.of(1970, 1, 1).atStartOfDay());
        sessao.setDataInicio(LocalDate.of(1970, 1, 1).atStartOfDay());
        sessao.setId(1L);
        sessao.setPauta(pauta);

        Voto voto = new Voto();
        voto.setAssociadoId("42");
        voto.setId(1L);
        voto.setSessao(sessao);
        voto.setVoto(true);
        when(votoService.registrarVoto(Mockito.<VotoDTO>any())).thenReturn(voto);

        VotoDTO votoDTO = new VotoDTO();
        votoDTO.setAssociadoId("42");
        votoDTO.setSessaoId(1L);
        votoDTO.setVoto(true);
        String content = (new ObjectMapper()).writeValueAsString(votoDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/votos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(votoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"sessao\":{\"id\":1,\"pauta\":{\"id\":1,\"titulo\":\"Titulo\"},\"dataInicio\":[1970,1,1,0,0],\"dataFim\":"
                                        + "[1970,1,1,0,0]},\"associadoId\":\"42\",\"voto\":true}"));
    }
}
