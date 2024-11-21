package br.com.fiap.gs.GlobalSolution.dto.anuncio;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DtoAtualizarAnuncio(
        @NotNull(message = "ID é obrigatório")
        Long id,
        Double energia,
        BigDecimal valor
) {
}
