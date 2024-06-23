package com.cooperativa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cooperativa.dto.ResultadoDTO;
import com.cooperativa.model.Pauta;
import com.cooperativa.model.Sessao;
import com.cooperativa.model.Voto;
import com.cooperativa.repository.PautaRepository;
import com.cooperativa.repository.SessaoRepository;
import com.cooperativa.repository.VotoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SessaoService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SessaoServiceTest {
    @MockBean
    private NotificacaoService notificacaoService;

    @MockBean
    private PautaRepository pautaRepository;

    @MockBean
    private SessaoRepository sessaoRepository;

    @Autowired
    private SessaoService sessaoService;

    @MockBean
    private VotoRepository votoRepository;


    @Test
    void testAbrirSessao() {
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setTitulo("Titulo");
        Optional<Pauta> ofResult = Optional.of(pauta);
        when(pautaRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Pauta pauta2 = new Pauta();
        pauta2.setId(1L);
        pauta2.setTitulo("Titulo");

        Sessao sessao = new Sessao();
        sessao.setDataFim(LocalDate.of(1970, 1, 1).atStartOfDay());
        sessao.setDataInicio(LocalDate.of(1970, 1, 1).atStartOfDay());
        sessao.setId(1L);
        sessao.setPauta(pauta2);
        when(sessaoRepository.save(Mockito.<Sessao>any())).thenReturn(sessao);

        Sessao actualAbrirSessaoResult = sessaoService.abrirSessao(1L, 1L);

        verify(pautaRepository).findById(eq(1L));
        verify(sessaoRepository).save(isA(Sessao.class));
        assertSame(sessao, actualAbrirSessaoResult);
    }


    @Test
    void testResultadoVotacao() {

        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setTitulo("Titulo");

        Sessao sessao = new Sessao();
        sessao.setDataFim(LocalDate.of(1970, 1, 1).atStartOfDay());
        sessao.setDataInicio(LocalDate.of(1970, 1, 1).atStartOfDay());
        sessao.setId(1L);
        sessao.setPauta(pauta);
        Optional<Sessao> ofResult = Optional.of(sessao);
        when(sessaoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(votoRepository.findBySessao(Mockito.<Sessao>any())).thenReturn(new ArrayList<>());


        ResultadoDTO actualResultadoVotacaoResult = sessaoService.resultadoVotacao(1L);


        verify(votoRepository).findBySessao(isA(Sessao.class));
        verify(sessaoRepository).findById(eq(1L));
        assertEquals(0L, actualResultadoVotacaoResult.getVotosNAO());
        assertEquals(0L, actualResultadoVotacaoResult.getVotosSIM());
    }


    @Test
    void testResultadoVotacao2() {
       // doNothing().when(notificacaoService).sendNotification(Mockito.<String>any(), Mockito.<String>any());
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setTitulo("Titulo");
        Sessao sessao = new Sessao();
        sessao.setDataFim(LocalDate.of(1970, 1, 1).atStartOfDay());
        sessao.setDataInicio(LocalDate.of(1970, 1, 1).atStartOfDay());
        sessao.setId(1L);
        sessao.setPauta(pauta);
        Optional<Sessao> ofResult = Optional.of(sessao);
        when(sessaoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Pauta pauta2 = new Pauta();
        pauta2.setId(1L);
        pauta2.setTitulo("votacao_resultado");
        Sessao sessao2 = new Sessao();
        sessao2.setDataFim(LocalDate.of(1970, 1, 1).atStartOfDay());
        sessao2.setDataInicio(LocalDate.of(1970, 1, 1).atStartOfDay());
        sessao2.setId(1L);
        sessao2.setPauta(pauta2);
        Voto voto = new Voto();
        voto.setAssociadoId("42");
        voto.setId(1L);
        voto.setSessao(sessao2);
        voto.setVoto(true);
        ArrayList<Voto> votoList = new ArrayList<>();
        votoList.add(voto);
        when(votoRepository.findBySessao(Mockito.<Sessao>any())).thenReturn(votoList);
        ResultadoDTO actualResultadoVotacaoResult = sessaoService.resultadoVotacao(1L);
        verify(votoRepository).findBySessao(isA(Sessao.class));
      //  verify(notificacaoService).sendNotification(eq("votacao_resultado"), eq("ResultadoDTO(votosSIM=1, votosNAO=0)"));
        verify(sessaoRepository).findById(eq(1L));
        assertEquals(0L, actualResultadoVotacaoResult.getVotosNAO());
        assertEquals(1L, actualResultadoVotacaoResult.getVotosSIM());
    }

}
