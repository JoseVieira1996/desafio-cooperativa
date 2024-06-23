package com.cooperativa.controller;

import static org.mockito.Mockito.when;

import com.cooperativa.dto.ResultadoDTO;
import com.cooperativa.dto.SessaoDTO;
import com.cooperativa.model.Pauta;
import com.cooperativa.model.Sessao;
import com.cooperativa.service.SessaoService;
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

@ContextConfiguration(classes = {SessaoController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SessaoControllerTest {
    @Autowired
    private SessaoController sessaoController;

    @MockBean
    private SessaoService sessaoService;


    @Test
    void testAbrirSessao() throws Exception {
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setTitulo("Titulo");

        Sessao sessao = new Sessao();
        sessao.setDataFim(LocalDate.of(1970, 1, 1).atStartOfDay());
        sessao.setDataInicio(LocalDate.of(1970, 1, 1).atStartOfDay());
        sessao.setId(1L);
        sessao.setPauta(pauta);
        when(sessaoService.abrirSessao(Mockito.<Long>any(), Mockito.<Long>any())).thenReturn(sessao);

        SessaoDTO sessaoDTO = new SessaoDTO();
        sessaoDTO.setDuracao(1L);
        sessaoDTO.setPautaId(1L);
        String content = (new ObjectMapper()).writeValueAsString(sessaoDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/sessoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(sessaoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"pauta\":{\"id\":1,\"titulo\":\"Titulo\"},\"dataInicio\":[1970,1,1,0,0],\"dataFim\":[1970,1,1,0,0]}"));
    }


    @Test
    void testResultadoVotacao() throws Exception {
        ResultadoDTO buildResult = ResultadoDTO.builder().votosNAO(1L).votosSIM(1L).build();
        when(sessaoService.resultadoVotacao(Mockito.<Long>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/sessoes/{id}/resultado", 1L);

        MockMvcBuilders.standaloneSetup(sessaoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"votosSIM\":1,\"votosNAO\":1}"));
    }
}
