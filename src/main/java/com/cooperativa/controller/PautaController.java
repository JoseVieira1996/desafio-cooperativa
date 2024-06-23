package com.cooperativa.controller;

import com.cooperativa.dto.PautaDTO;
import com.cooperativa.model.Pauta;
import com.cooperativa.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pautas")
public class PautaController {

    @Autowired
    private PautaService pautaService;

    @PostMapping
    public Pauta criarPauta(@RequestBody PautaDTO pautaDTO) {
        Pauta pauta = new Pauta();
        pauta.setTitulo(pautaDTO.getTitulo());
        return pautaService.criarPauta(pauta);
    }
}
