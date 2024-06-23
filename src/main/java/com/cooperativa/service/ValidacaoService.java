package com.cooperativa.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ValidacaoService {
    private static final String VALIDACAO_URL = "https://run.mocky.io/v3/bc13ee59-4783-491d-ac7a-d1ea4bdb7bf4";

    public boolean isAssociadoAbleToVote(String cpf) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String response = restTemplate.getForObject(VALIDACAO_URL, String.class);
            return response.contains(cpf);
        } catch (Exception e) {
            return false;
        }
    }
}
