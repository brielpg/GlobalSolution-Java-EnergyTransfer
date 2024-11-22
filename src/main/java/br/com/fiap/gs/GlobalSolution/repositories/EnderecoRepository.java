package br.com.fiap.gs.GlobalSolution.repositories;

import br.com.fiap.gs.GlobalSolution.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
