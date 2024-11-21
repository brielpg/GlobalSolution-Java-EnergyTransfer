package br.com.fiap.gs.GlobalSolution.controllers;

import br.com.fiap.gs.GlobalSolution.dto.usuario.*;
import br.com.fiap.gs.GlobalSolution.services.UsuarioService;
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
@RequestMapping("/usuario")
@Tag(name = "Usuario", description = "Endpoints para gerenciar usuários")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Transactional
    @Operation(
            summary = "Cria um novo usuário",
            description = "Este endpoint permite que o usuário crie um novo usuário fornecendo os dados necessários, como nome, CPF, email e endereço. Sendo que cada cpf é único por Usuário" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Já existe um usuário registrado com esse CPF"),
            @ApiResponse(responseCode = "200", description = "Usuário reativado com sucesso")
    })
    public ResponseEntity<?> createUsuario(@RequestBody @Valid DtoCriarUsuario dados) {
        return usuarioService.createUsuario(dados);
    }

    @PostMapping("/login")
    @Transactional
    @Operation(
            summary = "Autenticação de usuário",
            description = "Este endpoint permite que o usuário faça login fornecendo o email e a senha." )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Email ou Senha incorretos")
    })
    public ResponseEntity<?> authUsuario(@RequestBody @Valid DtoRequestLogin dados) {
        return usuarioService.authUsuario(dados);
    }

    @PutMapping
    @Transactional
    @Operation(
            summary = "Atualiza um usuário existente",
            description = "Este endpoint permite que o usuário atualize seus dados fornecendo id e as novas informações." )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<?> updateUsuario(@RequestBody @Valid DtoAtualizarUsuario dados) {
        return usuarioService.updateUsuario(dados);
    }

    @GetMapping
    @Operation(
            summary = "Lista todos os usuários",
            description = "Este endpoint retorna uma lista paginada de todos os usuários." )
   @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    public Page<DtoListarUsuario> getAllUsuarios(Pageable paginacao) {
        return usuarioService.getAllUsuarios(paginacao);
    }

    @GetMapping("/{cpf}")
    @Operation(
            summary = "Obtém detalhes de um usuário pelo CPF",
            description = "Este endpoint permite que o usuário obtenha os detalhes de um usuário específico fornecendo o CPF." )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhes do usuário retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário com este CPF não foi encontrado"),
            @ApiResponse(responseCode = "409", description = "Usuário está desativado")
    })
    public ResponseEntity<?> getUsuarioByCpf(@PathVariable String cpf) {
        return usuarioService.getUsuarioByCpf(cpf);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(
            summary = "Desativa um usuário",
            description = "Este endpoint permite que o usuário desative um usuário específico fornecendo o ID do usuário." )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Usuário já está desativado")
    })
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id){
        return usuarioService.deleteUsuario(id);
    }
}
