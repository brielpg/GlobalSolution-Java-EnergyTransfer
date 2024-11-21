package br.com.fiap.gs.GlobalSolution.controllers;

import br.com.fiap.gs.GlobalSolution.dto.anuncio.DtoAtualizarAnuncio;
import br.com.fiap.gs.GlobalSolution.dto.anuncio.DtoCompraAnuncio;
import br.com.fiap.gs.GlobalSolution.dto.anuncio.DtoCriarAnuncio;
import br.com.fiap.gs.GlobalSolution.dto.anuncio.DtoListarAnuncio;
import br.com.fiap.gs.GlobalSolution.services.AnuncioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/anuncio")
@Tag(name = "Anuncio", description = "Endpoints para gerenciar anúncios")
public class AnuncioController {
    @Autowired
    private AnuncioService anuncioService;

    @PostMapping
    @Transactional
    @Operation(
            summary = "Cria um novo anúncio",
            description = "Este endpoint permite que um usuário crie um novo anúncio fornecendo os dados necessários, como quantidade de energia, preço/valor e id do vendedor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Anúncio criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vendedor com este Id não foi encontrado"),
            @ApiResponse(responseCode = "409", description = "A conta deste vendedor está desativada")
    })
    public ResponseEntity<?> createAnuncio(@RequestBody @Valid DtoCriarAnuncio dados) {
        return anuncioService.createAnuncio(dados);
    }

    @PostMapping("/comprar")
    @Transactional
    @Operation(
            summary = "Compra um anúncio",
            description = "Este endpoint permite que um usuário realize a compra de um anúncio existente, fornecendo o id do anúncio e o id do comprador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anúncio comprado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anúncio ou usuário comprador não encontrado"),
            @ApiResponse(responseCode = "409", description = "Anúncio já foi vendido")
    })
    public ResponseEntity<?> buyAnuncio(@RequestBody @Valid DtoCompraAnuncio dados) {
        return anuncioService.buyAnuncio(dados);
    }

    @PutMapping
    @Transactional
    @Operation(
            summary = "Atualiza as informaçoes de um anúncio existente",
            description = "Este endpoint permite que o usuário atualize as informaçoes de um anúncio existente, fornecendo o id do anúncio e os novos dados para atualização." )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anúncio atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anúncio não encontrado"),
            @ApiResponse(responseCode = "400", description = "Este anúncio já foi encerrado")
    })
    public ResponseEntity<?> updateAnuncio(@RequestBody @Valid DtoAtualizarAnuncio dados) {
        return anuncioService.updateAnuncio(dados);
    }

    @GetMapping
    @Operation(
            summary = "Lista todos os anúncios disponíveis",
            description = "Este endpoint retorna uma lista paginada de todos os anúncios que estão atualmente disponíveis." )
    @ApiResponse(responseCode = "200", description = "Lista de anúncios retornada com sucesso")
    public Page<DtoListarAnuncio> getAllAnuncios(Pageable paginacao) {
        return anuncioService.getAllAnuncios(paginacao);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Lista as informaçoes de um anúncio pelo Id",
            description = "Este endpoint permite que o usuário obtenha as informaçoes de um anúncio específico fornecendo seu Id." )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhes do anúncio retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anúncio com este Id não foi encontrado")
    })
    public ResponseEntity<?> getAnuncioById(@PathVariable Long id) {
        return anuncioService.getAnuncioById(id);
    }
}
