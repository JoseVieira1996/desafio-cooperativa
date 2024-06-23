package com.cooperativa.service;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ValidacaoService.class})
@ExtendWith(SpringExtension.class)
class ValidacaoServiceTest {
    @Autowired
    private ValidacaoService validacaoService;


    @Test
    void testIsAssociadoAbleToVote() {
        assertFalse(validacaoService.isAssociadoAbleToVote("Cpf"));
        assertFalse(validacaoService.isAssociadoAbleToVote("U://U@[9U]:{UU?U#U"));
    }
}
