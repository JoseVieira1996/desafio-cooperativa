package com.cooperativa.converter;

import com.cooperativa.dto.VotoDTO;
import com.cooperativa.model.Pauta;
import com.cooperativa.model.Sessao;
import com.cooperativa.model.Voto;

import java.time.LocalDateTime;
import java.util.Optional;

public class Converter {

    public static Voto converterVoto(VotoDTO votoDTO, Sessao sessao) {
        Voto novoVoto = new Voto();
        novoVoto.setSessao(sessao);
        novoVoto.setAssociadoId(votoDTO.getAssociadoId());
        novoVoto.setVoto(votoDTO.getVoto());
        return novoVoto;
    }


    public static Sessao getSessao(Long duracao, Optional<Pauta> pautaOptional) {
        Sessao sessao = new Sessao();
        sessao.setPauta(pautaOptional.get());
        sessao.setDataInicio(LocalDateTime.now());
        sessao.setDataFim(LocalDateTime.now().plusMinutes(duracao != null ? duracao : 1));
        return sessao;
    }
}
