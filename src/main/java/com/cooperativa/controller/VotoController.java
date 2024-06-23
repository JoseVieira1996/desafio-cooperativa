package com.cooperativa.controller;

import com.cooperativa.dto.VotoDTO;
import com.cooperativa.model.Voto;
import com.cooperativa.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/votos")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @PostMapping
    public Voto votar(@RequestBody VotoDTO votoDTO) {
        return votoService.registrarVoto(votoDTO);
    }
}
