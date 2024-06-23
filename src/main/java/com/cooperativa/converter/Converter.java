package com.cooperativa.converter;

import com.cooperativa.dto.VotoDTO;
import com.cooperativa.model.Sessao;
import com.cooperativa.model.Voto;

public class Converter {

    public static Voto converterVoto(VotoDTO votoDTO, Sessao sessao) {
        Voto novoVoto = new Voto();
        novoVoto.setSessao(sessao);
        novoVoto.setAssociadoId(votoDTO.getAssociadoId());
        novoVoto.setVoto(votoDTO.getVoto());
        return novoVoto;
    }
}
