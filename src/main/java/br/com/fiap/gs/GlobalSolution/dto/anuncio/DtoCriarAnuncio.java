package br.com.fiap.gs.GlobalSolution.dto.anuncio;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DtoCriarAnuncio(
        @NotNull(message = "Quantidade de Energia é obrigatória")
        Double energia,
        @NotNull(message = "Valor é obrigatório")
        BigDecimal valor,
        @NotNull(message = "Vendedor é obrigatório")
        Long vendedorId
){
}
