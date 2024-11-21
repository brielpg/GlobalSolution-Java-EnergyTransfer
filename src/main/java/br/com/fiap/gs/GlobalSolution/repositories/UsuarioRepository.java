package br.com.fiap.gs.GlobalSolution.repositories;

import br.com.fiap.gs.GlobalSolution.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByCpf(String cnpj);
    Usuario findByEmail(String email);
    Usuario findByCpf(String cpf);
    Usuario getReferenceByCpf(String cpf);
}
