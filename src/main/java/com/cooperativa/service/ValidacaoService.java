package com.cooperativa.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ValidacaoService {

    @Value("${url.cpf}")
    private String VALIDACAO_URL;

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
