package com.cooperativa.service;

import com.cooperativa.converter.Converter;
import com.cooperativa.dto.ResultadoDTO;
import com.cooperativa.exception.ResourceNotFoundException;
import com.cooperativa.model.Pauta;
import com.cooperativa.model.Sessao;
import com.cooperativa.model.Voto;
import com.cooperativa.repository.PautaRepository;
import com.cooperativa.repository.SessaoRepository;
import com.cooperativa.repository.VotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SessaoService {
    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private NotificacaoService notificationService;

    public Sessao abrirSessao(Long pautaId, Long duracao) {
        Optional<Pauta> pautaOptional = pautaRepository.findById(pautaId);
        if (pautaOptional.isEmpty()) {
            throw new ResourceNotFoundException("Pauta não encontrada");
        }

        var sessao = Converter.getSessao(duracao, pautaOptional);
        return sessaoRepository.save(sessao);
    }

    public ResultadoDTO resultadoVotacao(Long sessaoId) {
        Sessao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Sessão não encontrada"));

        List<Voto> votos = votoRepository.findBySessao(sessao);

        long votosSim = votos.stream().filter(Voto::getVoto).count();
        long votosNao = votos.stream().filter(v -> !v.getVoto()).count();

        log.info("Resultado: " + votosSim + " SIM, " + votosNao + " NÃO");
        var resultado = ResultadoDTO.builder()
                .votosSIM(votosSim)
                .votosNAO(votosNao)
                .build();

        return resultado;
    }
}
