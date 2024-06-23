package com.cooperativa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cooperativa.model.Pauta;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
}
