package com.cooperativa.service;

import com.cooperativa.model.Pauta;
import com.cooperativa.model.Sessao;
import com.cooperativa.model.Voto;
import com.cooperativa.repository.PautaRepository;
import com.cooperativa.repository.SessaoRepository;
import com.cooperativa.repository.VotoRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessaoService {
    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private NotificationService notificationService;

    public Sessao abrirSessao(Long pautaId, Long duracao) {
        Optional<Pauta> pautaOptional = pautaRepository.findById(pautaId);
        if (pautaOptional.isEmpty()) {
            throw new ResourceNotFoundException("Pauta não encontrada");
        }

        Sessao sessao = new Sessao();
        sessao.setPauta(pautaOptional.get());
        sessao.setDataInicio(LocalDateTime.now());
        sessao.setDataFim(LocalDateTime.now().plusMinutes(duracao != null ? duracao : 1));

        return sessaoRepository.save(sessao);
    }

    public String resultadoVotacao(Long sessaoId) {
        Sessao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Sessão não encontrada"));

        List<Voto> votos = votoRepository.findBySessao(sessao);

        long votosSim = votos.stream().filter(Voto::getVoto).count();
        long votosNao = votos.stream().filter(v -> !v.getVoto()).count();

        String resultado = "Resultado: " + votosSim + " SIM, " + votosNao + " NÃO";
        notificationService.sendNotification("votacao_resultado", resultado);

        return resultado;
    }
}
