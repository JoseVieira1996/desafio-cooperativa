package com.cooperativa.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cooperativa.dto.PautaDTO;
import com.cooperativa.model.Pauta;
import com.cooperativa.repository.PautaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PautaService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class PautaServiceTest {
    @MockBean
    private PautaRepository pautaRepository;

    @Autowired
    private PautaService pautaService;


    @Test
    void testCriarPauta() {
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setTitulo("Titulo");
        when(pautaRepository.save(Mockito.<Pauta>any())).thenReturn(pauta);

        PautaDTO pauta2 = new PautaDTO();
        pauta2.setTitulo("Titulo");

        Pauta actualCriarPautaResult = pautaService.criarPauta(pauta2);

        verify(pautaRepository).save(isA(Pauta.class));
        assertSame(pauta, actualCriarPautaResult);
    }
}
