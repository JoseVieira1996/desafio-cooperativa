package com.cooperativa.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sessao_id")
    private Sessao sessao;

    private String associadoId;
    private Boolean voto;

}
