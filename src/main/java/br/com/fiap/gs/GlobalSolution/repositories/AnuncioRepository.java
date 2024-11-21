package br.com.fiap.gs.GlobalSolution.repositories;

import br.com.fiap.gs.GlobalSolution.enums.EtStatus;
import br.com.fiap.gs.GlobalSolution.models.Anuncio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

    Page<Anuncio> findAllByStatus(EtStatus etStatus, Pageable paginacao);
}
