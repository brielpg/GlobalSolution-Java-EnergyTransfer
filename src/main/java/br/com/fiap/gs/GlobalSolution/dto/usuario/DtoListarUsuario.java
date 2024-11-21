package br.com.fiap.gs.GlobalSolution.dto.usuario;

import br.com.fiap.gs.GlobalSolution.models.Endereco;
import br.com.fiap.gs.GlobalSolution.models.Usuario;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

public class DtoListarUsuario extends RepresentationModel<DtoListarUsuario>{
        public Long id;
        public String nome;
        public String cpf;
        public String email;
        public LocalDate dataNascimento;
        public Boolean ativo;
        public String rg;
        public String telefone;
        public LocalDate dataCadastro;
        public Endereco endereco;

    public DtoListarUsuario(Usuario usuario){
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.cpf = usuario.getCpf();
        this.email = usuario.getEmail();
        this.dataNascimento = usuario.getDataNascimento();
        this.ativo = usuario.getAtivo();
        this.rg = usuario.getRg();
        this.telefone = usuario.getTelefone();
        this.dataCadastro = usuario.getDataCadastro();
        this.endereco = usuario.getEndereco();
    }
}
