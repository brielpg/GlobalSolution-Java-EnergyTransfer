package br.com.fiap.gs.GlobalSolution.dto.usuario;

import br.com.fiap.gs.GlobalSolution.dto.DtoEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record DtoCriarUsuario(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF inválido. O formato deve ser 000.000.000-00")
        @NotBlank(message = "CPF é obrigatório")
        String cpf,

        @Email(message = "Email inválido")
        @NotBlank(message = "Email é obrigatório")
        String email,

        @NotNull(message = "Data de Nascimento é obrigatória")
        LocalDate dataNascimento,

        @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}-\\d$", message = "RG inválido. O formato deve ser 00.000.000-0")
        @NotBlank(message = "RG é obrigatório")
        String rg,

        @NotNull(message = "Telefone é obrigatório")
        String telefone,

        @NotNull(message = "Senha é obrigatória")
        String senha,

        @NotNull(message = "Endereço é obrigatório")
        @Valid
        DtoEndereco endereco
) {
}
