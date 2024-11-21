package br.com.fiap.gs.GlobalSolution.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DtoRequestLogin(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String senha
) {
}
