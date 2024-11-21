package br.com.fiap.gs.GlobalSolution.dto.usuario;

import br.com.fiap.gs.GlobalSolution.dto.DtoEndereco;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DtoAtualizarUsuario(
        @NotNull(message = "Id é obrigatório")
        Long id,
        String nome,
        String cpf,
        String email,
        LocalDate dataNascimento,
        String rg,
        String telefone,
        String senha,
        DtoEndereco endereco
) {
}
