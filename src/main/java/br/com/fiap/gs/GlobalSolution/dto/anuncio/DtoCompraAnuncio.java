package br.com.fiap.gs.GlobalSolution.dto.anuncio;

import jakarta.validation.constraints.NotNull;

public record DtoCompraAnuncio(
        @NotNull(message = "Anúncio é obrigatório")
        Long anuncioId,
        @NotNull(message = "Comprador é obrigatório")
        Long compradorId
){
}
