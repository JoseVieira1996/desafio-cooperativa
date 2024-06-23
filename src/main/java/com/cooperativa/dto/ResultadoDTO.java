package com.cooperativa.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultadoDTO {

    private long votosSIM;
    private long votosNAO;
}
