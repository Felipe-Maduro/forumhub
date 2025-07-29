package com.forumhub.forumhub.controller;

import com.forumhub.forumhub.dto.topico.DadosAtualizacaoTopico;
import com.forumhub.forumhub.dto.topico.DadosCadastroTopico;
import com.forumhub.forumhub.dto.topico.DadosDetalheTopico;
import com.forumhub.forumhub.model.Topic;
import com.forumhub.forumhub.service.TopicoService;
import com.forumhub.forumhub.user.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
@RequiredArgsConstructor
public class TopicoController {

    private final TopicoService service;

    @PostMapping
    public ResponseEntity<DadosDetalheTopico> criar(@AuthenticationPrincipal Usuario usuario,
                                                    @RequestBody @Valid DadosCadastroTopico dados) {
        Topic t = service.criar(usuario, dados);
        return ResponseEntity.ok(map(t));
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalheTopico>> listar(Pageable pageable) {
        var page = service.listar(pageable).map(this::map);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalheTopico> detalhar(@PathVariable Long id) {
        var t = service.detalhar(id);
        return ResponseEntity.ok(map(t));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalheTopico> atualizar(@PathVariable Long id,
                                                        @AuthenticationPrincipal Usuario usuario,
                                                        @RequestBody @Valid DadosAtualizacaoTopico dados) {
        var t = service.atualizar(id, usuario, dados);
        return ResponseEntity.ok(map(t));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id,
                                        @AuthenticationPrincipal Usuario usuario) {
        service.excluir(id, usuario);
        return ResponseEntity.noContent().build();
    }

    private DadosDetalheTopico map(Topic t) {
        return new DadosDetalheTopico(
                t.getId(),
                t.getTitulo(),
                t.getMensagem(),
                t.getDataCriacao(),
                t.getDataAtualizacao(),
                t.getStatus(),
                t.getAutor().getLogin()
        );
    }
}

