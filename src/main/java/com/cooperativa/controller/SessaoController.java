package com.cooperativa.controller;

import com.cooperativa.dto.SessaoDTO;
import com.cooperativa.model.Sessao;
import com.cooperativa.service.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sessoes")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    @PostMapping
    public Sessao abrirSessao(@RequestBody SessaoDTO sessaoDTO) {
        return sessaoService.abrirSessao(sessaoDTO.getPautaId(), sessaoDTO.getDuracao());
    }

    @GetMapping("/{id}/resultado")
    public String resultadoVotacao(@PathVariable Long id) {
        return sessaoService.resultadoVotacao(id);
    }
}
