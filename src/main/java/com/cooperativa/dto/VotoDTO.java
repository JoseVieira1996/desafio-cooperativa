package com.cooperativa.dto;

import lombok.Data;

@Data
public class VotoDTO {

    private Long sessaoId;
    private String associadoId;
    private Boolean voto;

}
