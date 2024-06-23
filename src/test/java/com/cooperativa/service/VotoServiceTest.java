package com.cooperativa.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cooperativa.dto.VotoDTO;
import com.cooperativa.exception.IllegalArgumentException;
import com.cooperativa.model.Pauta;
import com.cooperativa.model.Sessao;
import com.cooperativa.model.Voto;
import com.cooperativa.repository.SessaoRepository;
import com.cooperativa.repository.VotoRepository;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VotoService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class VotoServiceTest {
    @MockBean
    private SessaoRepository sessaoRepository;

    @MockBean
    private ValidacaoService validacaoService;

    @MockBean
    private VotoRepository votoRepository;

    @Autowired
    private VotoService votoService;


    @Test
    void testRegistrarVoto() {
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
        when(votoRepository.existsBySessaoAndAssociadoId(Mockito.<Sessao>any(), Mockito.<String>any())).thenReturn(true);

        VotoDTO votoDTO = new VotoDTO();
        votoDTO.setAssociadoId("42");
        votoDTO.setSessaoId(1L);
        votoDTO.setVoto(true);

        assertThrows(IllegalArgumentException.class, () -> votoService.registrarVoto(votoDTO));
        verify(votoRepository).existsBySessaoAndAssociadoId(isA(Sessao.class), eq("42"));
        verify(sessaoRepository).findById(eq(1L));
    }


    @Test
    void testRegistrarVoto2() {
        // Arrange
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
        when(validacaoService.isAssociadoAbleToVote(Mockito.<String>any())).thenReturn(true);

        Pauta pauta2 = new Pauta();
        pauta2.setId(1L);
        pauta2.setTitulo("Titulo");

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
        when(votoRepository.existsBySessaoAndAssociadoId(Mockito.<Sessao>any(), Mockito.<String>any())).thenReturn(false);
        when(votoRepository.save(Mockito.<Voto>any())).thenReturn(voto);

        VotoDTO votoDTO = new VotoDTO();
        votoDTO.setAssociadoId("42");
        votoDTO.setSessaoId(1L);
        votoDTO.setVoto(true);

        Voto actualRegistrarVotoResult = votoService.registrarVoto(votoDTO);

        verify(votoRepository).existsBySessaoAndAssociadoId(isA(Sessao.class), eq("42"));
        verify(validacaoService).isAssociadoAbleToVote(eq("42"));
        verify(sessaoRepository).findById(eq(1L));
        verify(votoRepository).save(isA(Voto.class));
        assertSame(voto, actualRegistrarVotoResult);
    }
}
