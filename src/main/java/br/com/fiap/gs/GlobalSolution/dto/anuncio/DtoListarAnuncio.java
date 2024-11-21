package br.com.fiap.gs.GlobalSolution.dto.anuncio;

import br.com.fiap.gs.GlobalSolution.enums.EtStatus;
import br.com.fiap.gs.GlobalSolution.models.Anuncio;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DtoListarAnuncio extends RepresentationModel<DtoListarAnuncio> {
    public Long id;
    public EtStatus status;
    public LocalDate dataAnuncio;
    public Double energia;
    public BigDecimal valor;
    public String vendedor;
    public String comprador;

    public DtoListarAnuncio(Anuncio anuncio){
        this.id = anuncio.getId();
        this.status = anuncio.getStatus();
        this.dataAnuncio = anuncio.getDataAnuncio();
        this.energia = anuncio.getEnergia();
        this.valor = anuncio.getValor();
        this.vendedor = anuncio.getVendedor().getNome();
        if (anuncio.getComprador() != null){
            this.comprador = anuncio.getComprador().getNome();
        }
    }
}
