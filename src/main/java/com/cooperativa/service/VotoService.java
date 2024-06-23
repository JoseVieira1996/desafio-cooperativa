package com.cooperativa.service;

import com.cooperativa.converter.Converter;
import com.cooperativa.dto.VotoDTO;
import com.cooperativa.exception.IllegalArgumentException;
import com.cooperativa.exception.ResourceNotFoundException;
import com.cooperativa.model.Sessao;
import com.cooperativa.model.Voto;
import com.cooperativa.repository.SessaoRepository;
import com.cooperativa.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VotoService {
    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private SessaoRepository sessaoRepository;


    @Autowired
    private ValidacaoService validacaoService;

    public Voto registrarVoto(VotoDTO votoDTO) {
        Sessao sessao = sessaoRepository.findById(votoDTO.getSessaoId())
                .orElseThrow(() -> new ResourceNotFoundException("Sessao não encontrada"));

        if (votoRepository.existsBySessaoAndAssociadoId(sessao, votoDTO.getAssociadoId())) {
            throw new IllegalArgumentException("Associado já votou nesta sessão");
        }

        if (!validacaoService.isAssociadoAbleToVote(votoDTO.getAssociadoId())) {
            throw new IllegalArgumentException("Associado não está autorizado a votar");
        }

        Voto novoVoto = Converter.converterVoto(votoDTO, sessao);

        return votoRepository.save(novoVoto);
    }


}

