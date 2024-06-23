package com.cooperativa.service;

import com.cooperativa.dto.PautaDTO;
import com.cooperativa.model.Pauta;
import com.cooperativa.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;



    public Pauta criarPauta(PautaDTO pautaDTO) {
        Pauta pauta = new Pauta();
        pauta.setTitulo(pautaDTO.getTitulo());
        return pautaRepository.save(pauta);
    }
}
