package br.com.fiap.gs.GlobalSolution.services;

import br.com.fiap.gs.GlobalSolution.controllers.AnuncioController;
import br.com.fiap.gs.GlobalSolution.controllers.UsuarioController;
import br.com.fiap.gs.GlobalSolution.dto.anuncio.DtoAtualizarAnuncio;
import br.com.fiap.gs.GlobalSolution.dto.anuncio.DtoCompraAnuncio;
import br.com.fiap.gs.GlobalSolution.dto.anuncio.DtoCriarAnuncio;
import br.com.fiap.gs.GlobalSolution.dto.anuncio.DtoListarAnuncio;
import br.com.fiap.gs.GlobalSolution.enums.EtStatus;
import br.com.fiap.gs.GlobalSolution.models.Anuncio;
import br.com.fiap.gs.GlobalSolution.repositories.AnuncioRepository;
import br.com.fiap.gs.GlobalSolution.repositories.UsuarioRepository;
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
public class AnuncioService {
    @Autowired
    private AnuncioRepository anuncioRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public ResponseEntity<?> createAnuncio(DtoCriarAnuncio dados) {
        var vendedorFind = usuarioRepository.findById(dados.vendedorId());

        if (vendedorFind.isPresent()) {
            if (vendedorFind.get().getAtivo().equals(false)){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: A conta deste vendedor está desativada.");
            }

            var anuncio = new Anuncio(dados);
            var vendedor = vendedorFind.get();
            anuncio.setVendedor(vendedor);

            anuncioRepository.save(anuncio);

            var retorno = new DtoListarAnuncio(anuncio);

            retorno.add(
                    linkTo(methodOn(AnuncioController.class).getAllAnuncios(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION),
                    linkTo(methodOn(AnuncioController.class).getAnuncioById(anuncio.getId())).withSelfRel(),
                    linkTo(methodOn(UsuarioController.class).getUsuarioByCpf(vendedor.getCpf())).withRel("vendedor")
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(retorno);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Vendedor com este Id não foi encontrado");
    }

    @Transactional
    public ResponseEntity<?> updateAnuncio(DtoAtualizarAnuncio dados) {
        if (anuncioRepository.existsById(dados.id())){
            var anuncio = anuncioRepository.getReferenceById(dados.id());

            if (anuncio.getStatus() == EtStatus.VENDIDO){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Este anúncio já foi encerrado.");
            }

            anuncio.atualizarAnuncio(dados);

            var retorno = new DtoListarAnuncio(anuncio);

            retorno.add(
                    linkTo(methodOn(AnuncioController.class).getAllAnuncios(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION),
                    linkTo(methodOn(AnuncioController.class).getAnuncioById(anuncio.getId())).withSelfRel()
            );

            return ResponseEntity.status(HttpStatus.OK).body(retorno);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Anúncio não encontrado.");
    }

    @Transactional
    public ResponseEntity<?> buyAnuncio(DtoCompraAnuncio dados){
        var anuncio = anuncioRepository.findById(dados.anuncioId())
                .orElseThrow(() -> new RuntimeException("Erro: Anúncio não encontrado"));

        if (anuncio.getStatus() == EtStatus.VENDIDO) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Anúncio já foi vendido");
        }

        var comprador = usuarioRepository.findById(dados.compradorId())
                .orElseThrow(() -> new RuntimeException("Erro: Usuário comprador não encontrado"));

        anuncio.comprarAnuncio(comprador);

        var retorno = new DtoListarAnuncio(anuncio);

        retorno.add(
                linkTo(methodOn(AnuncioController.class).getAnuncioById(anuncio.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).getUsuarioByCpf(anuncio.getVendedor().getCpf())).withRel("vendedor"),
                linkTo(methodOn(UsuarioController.class).getUsuarioByCpf(anuncio.getComprador().getCpf())).withRel("comprador")
        );

        return ResponseEntity.status(HttpStatus.OK).body(retorno);
    }

    @Transactional
    public Page<DtoListarAnuncio> getAllAnuncios(Pageable paginacao) {
        var anuncios = anuncioRepository.findAllByStatus(EtStatus.DISPONIVEL, paginacao).map(DtoListarAnuncio::new);
        for (DtoListarAnuncio i : anuncios){
            i.add(linkTo(methodOn(AnuncioController.class).getAnuncioById(i.id)).withSelfRel());
        }

        return anuncios;
    }

    @Transactional
    public ResponseEntity<?> getAnuncioById(Long id) {
        var anuncioFind = anuncioRepository.findById(id);
        if (anuncioFind.isPresent()) {
            var anuncio = anuncioFind.get();
            var retorno = new DtoListarAnuncio(anuncio);

            retorno.add(
                    linkTo(methodOn(AnuncioController.class).getAllAnuncios(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION),
                    linkTo(methodOn(UsuarioController.class).getUsuarioByCpf(anuncio.getVendedor().getNome())).withRel("vendedor")
            );

            if (anuncio.getComprador() != null){
                retorno.add(linkTo(methodOn(UsuarioController.class).getUsuarioByCpf(anuncio.getComprador().getNome())).withRel("comprador"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(retorno);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Anúncio com este Id não foi encontrada");
    }
}
