package br.com.fiap.gs.GlobalSolution.services;

import br.com.fiap.gs.GlobalSolution.controllers.UsuarioController;
import br.com.fiap.gs.GlobalSolution.dto.usuario.*;
import br.com.fiap.gs.GlobalSolution.models.Usuario;
import br.com.fiap.gs.GlobalSolution.models.Endereco;
import br.com.fiap.gs.GlobalSolution.repositories.UsuarioRepository;
import br.com.fiap.gs.GlobalSolution.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public ResponseEntity<?> createUsuario(DtoCriarUsuario dados) {
        var usuario = usuarioRepository.findByCpf(dados.cpf());

        // VERIFICAR SE JA EXISTE UM USUARIO COM ESTE CPF
        if (usuario != null && usuario.getAtivo()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Já existe um usuário registrado com esse CPF");
        }

        // REATIVAR CONTA DESATIVADA
        if (usuario != null && !usuario.getAtivo()) {
            usuario.setAtivo(true);

            var retorno = new DtoListarUsuario(usuario);

            retorno.add(
                    linkTo(methodOn(UsuarioController.class).getAllUsuarios(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION),
                    linkTo(methodOn(UsuarioController.class).getUsuarioByCpf(usuario.getCpf())).withSelfRel()
            );

            return ResponseEntity.status(HttpStatus.OK).body(retorno);
        }

        // CRIAR NOVO USUARIO
        var endereco = enderecoRepository.save(new Endereco(dados.endereco()));
        var novoUsuario = new Usuario(dados);
        novoUsuario.setEndereco(endereco);
        usuarioRepository.save(novoUsuario);

        var retorno = new DtoListarUsuario(novoUsuario);

        retorno.add(
                linkTo(methodOn(UsuarioController.class).getAllUsuarios(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION),
                linkTo(methodOn(UsuarioController.class).getUsuarioByCpf(novoUsuario.getCpf())).withSelfRel()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(retorno);
    }

    @Transactional
    public ResponseEntity<?> authUsuario(DtoRequestLogin dados) {
        var usuario = usuarioRepository.findByEmail(dados.email());
        if (usuario != null && usuario.getSenha().equals(dados.senha())) {
            var retorno = new DtoListarUsuario(usuario);
            retorno.add(linkTo(methodOn(UsuarioController.class).getUsuarioByCpf(usuario.getCpf())).withSelfRel());

            return ResponseEntity.status(HttpStatus.OK).body(retorno);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro: Email ou Senha incorretos.");
    }

    @Transactional
    public ResponseEntity<?> updateUsuario(DtoAtualizarUsuario dados) {
        if (usuarioRepository.existsById(dados.id())){
            var usuario = usuarioRepository.getReferenceById(dados.id());
            usuario.atualizarUsuario(dados);

            var retorno = new DtoListarUsuario(usuario);

            retorno.add(
                    linkTo(methodOn(UsuarioController.class).getUsuarioByCpf(usuario.getCpf())).withSelfRel(),
                    linkTo(methodOn(UsuarioController.class).getAllUsuarios(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION)
                    );

            return ResponseEntity.status(HttpStatus.OK).body(retorno);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Usuário não encontrado.");
    }

    @Transactional
    public Page<DtoListarUsuario> getAllUsuarios(Pageable paginacao) {
        var usuarios = usuarioRepository.findAll(paginacao).map(DtoListarUsuario::new);

        usuarios.forEach(i -> i.add(linkTo(methodOn(UsuarioController.class).getUsuarioByCpf(i.cpf)).withSelfRel()));

        return usuarios;
    }

    @Transactional
    public ResponseEntity<?> getUsuarioByCpf(String cpf) {
        var usuario = usuarioRepository.findByCpf(cpf);
        if (usuario != null) {
            if (usuario.getAtivo().equals(true)) {
                var retorno = new DtoListarUsuario(usuario);

                retorno.add(
                        linkTo(methodOn(UsuarioController.class).getAllUsuarios(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION)
                );

                return ResponseEntity.status(HttpStatus.OK).body(retorno);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Usuário está desativado.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Usuário com este CPF não foi encontrado.");
    }

    @Transactional
    public ResponseEntity<?> deleteUsuario(Long id) {
        if (usuarioRepository.existsById(id)){
            var usuario = usuarioRepository.getReferenceById(id);
            if (usuario.getAtivo().equals(true)){
                usuario.setAtivo(false);
                return ResponseEntity.status(HttpStatus.OK).body("Usuário \""+usuario.getNome()+"\" DESATIVADO com Sucesso!");
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Usuário já está desativado.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Usuário nao encontrado.");
    }
}
