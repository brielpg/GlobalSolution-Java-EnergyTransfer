package br.com.fiap.gs.GlobalSolution.models;

import br.com.fiap.gs.GlobalSolution.dto.anuncio.DtoAtualizarAnuncio;
import br.com.fiap.gs.GlobalSolution.dto.anuncio.DtoCriarAnuncio;
import br.com.fiap.gs.GlobalSolution.enums.EtStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "et_anuncios")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of = "id")
public class Anuncio extends RepresentationModel<Anuncio> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private EtStatus status;
    private LocalDate dataAnuncio;
    private Double energia; // kWh
    private BigDecimal valor;
    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Usuario vendedor;
    @ManyToOne
    @JoinColumn(name = "comprador_id")
    private Usuario comprador;

    public Anuncio(DtoCriarAnuncio dados){
        this.energia = dados.energia();
        this.valor = dados.valor();
        this.status = EtStatus.DISPONIVEL;
        this.dataAnuncio = LocalDate.now();
    }

    public void atualizarAnuncio(DtoAtualizarAnuncio dados) {
        if (dados.energia() != null){
            this.energia = dados.energia();
        }
        if (dados.valor() != null){
            this.valor = dados.valor();
        }
    }

    public void comprarAnuncio(Usuario comprador){
        this.status = EtStatus.VENDIDO;
        this.comprador = comprador;
        this.vendedor.setQuantidadeVendas(vendedor.getQuantidadeVendas()+1);
        this.comprador.setQuantidadeCompras(comprador.getQuantidadeCompras()+1);
    }
}
