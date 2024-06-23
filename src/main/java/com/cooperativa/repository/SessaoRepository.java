package com.cooperativa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cooperativa.model.Sessao;
import org.springframework.stereotype.Repository;

@Repository

public interface SessaoRepository extends JpaRepository<Sessao, Long> {
}
