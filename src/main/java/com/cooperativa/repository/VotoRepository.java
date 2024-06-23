package com.cooperativa.repository;

import com.cooperativa.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cooperativa.model.Voto;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsBySessaoAndAssociadoId(Sessao sessao, String associadoId);

    List<Voto> findBySessao(Sessao sessao);
}
