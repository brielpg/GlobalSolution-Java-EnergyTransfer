package br.com.fiap.gs.GlobalSolution.models;

import br.com.fiap.gs.GlobalSolution.dto.usuario.DtoAtualizarUsuario;
import br.com.fiap.gs.GlobalSolution.dto.usuario.DtoCriarUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Entity
@Table(name = "et_usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of = "id")
public class Usuario extends RepresentationModel<Usuario> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private LocalDate dataNascimento;
    private String rg;
    private String telefone;
    private LocalDate dataCadastro;
    private Boolean ativo;
    private Integer quantidadeVendas;
    private Integer quantidadeCompras;
    @JsonIgnore
    private String senha;
    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    public Usuario(DtoCriarUsuario dados) {
        this.nome = dados.nome();
        this.cpf = dados.cpf();
        this.email = dados.email();
        this.dataNascimento = dados.dataNascimento();
        this.rg = dados.rg();
        this.telefone = dados.telefone();
        this.senha = dados.senha();

        this.ativo = true;
        this.dataCadastro = LocalDate.now();
        this.quantidadeVendas = 0;
        this.quantidadeCompras = 0;
    }

    public void atualizarUsuario(DtoAtualizarUsuario dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.cpf() != null){
            this.cpf = dados.cpf();
        }
        if (dados.email() != null) {
            this.email = dados.email();
        }
        if (dados.dataNascimento() != null){
            this.dataNascimento = dados.dataNascimento();
        }
        if (dados.rg() != null){
            this.rg = dados.rg();
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if (dados.senha() != null) {
            this.senha = dados.senha();
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarEndereco(dados.endereco());
        }
    }
}
