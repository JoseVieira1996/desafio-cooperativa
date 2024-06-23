package com.cooperativa.service;

import com.cooperativa.model.Pauta;
import com.cooperativa.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;



    public Pauta criarPauta(Pauta pauta) {
        return pautaRepository.save(pauta);
    }
}
