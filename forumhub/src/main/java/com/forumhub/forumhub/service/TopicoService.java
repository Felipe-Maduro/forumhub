package com.forumhub.forumhub.service;

import com.forumhub.forumhub.dto.topico.DadosAtualizacaoTopico;
import com.forumhub.forumhub.dto.topico.DadosCadastroTopico;
import com.forumhub.forumhub.model.Topic;
import com.forumhub.forumhub.repository.TopicoRepository;
import com.forumhub.forumhub.user.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TopicoService {

    private final TopicoRepository repository;

    @Transactional
    public Topic criar(Usuario autor, DadosCadastroTopico dados) {
        if (repository.existsByTituloAndAtivoTrue(dados.titulo())) {
            throw new IllegalArgumentException("Já existe tópico ativo com este título.");
        }
        var topico = Topic.builder()
                .titulo(dados.titulo())
                .mensagem(dados.mensagem())
                .autor(autor)
                .build();
        return repository.save(topico);
    }

    @Transactional(readOnly = true)
    public Page<Topic> listar(Pageable pageable) {
        return repository.findByAtivoTrue(pageable);
    }

    @Transactional(readOnly = true)
    public Topic detalhar(Long id) {
        var topico = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado."));
        if (!topico.isAtivo()) {
            throw new IllegalArgumentException("Tópico inativo.");
        }
        return topico;
    }

    @Transactional
    public Topic atualizar(Long id, Usuario solicitante, DadosAtualizacaoTopico dados) {
        var topico = detalhar(id);
        validarAutorOuAdmin(solicitante, topico);
        if (topico.getStatus() != null && topico.getStatus().name().equals("FECHADO")) {
            throw new IllegalStateException("Não é permitido atualizar um tópico fechado.");
        }
        topico.atualizar(dados.titulo(), dados.mensagem());
        return topico;
    }

    @Transactional
    public void excluir(Long id, Usuario solicitante) {
        var topico = detalhar(id);
        validarAutorOuAdmin(solicitante, topico);
        topico.desativar();
    }

    private void validarAutorOuAdmin(Usuario solicitante, Topic topico) {
        boolean admin = solicitante.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean autor = solicitante.getId().equals(topico.getAutor().getId());
        if (!admin && !autor) throw new AccessDeniedException("Operação não permitida.");
    }
}

