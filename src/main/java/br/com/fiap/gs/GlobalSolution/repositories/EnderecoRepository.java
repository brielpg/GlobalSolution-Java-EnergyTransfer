package br.com.fiap.gs.GlobalSolution.repositories;

import br.com.fiap.gs.GlobalSolution.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
