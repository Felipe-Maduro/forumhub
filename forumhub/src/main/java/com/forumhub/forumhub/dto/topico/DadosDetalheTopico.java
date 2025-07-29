package com.forumhub.forumhub.dto.topico;

import com.forumhub.forumhub.topico.StatusTopico;

import java.time.LocalDateTime;

public record DadosDetalheTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao,
        StatusTopico status,
        String autor
) {}

