package com.forumhub.forumhub.dto.topico;

import jakarta.validation.constraints.Size;

public record DadosAtualizacaoTopico(
        @Size(min = 5, max = 100) String titulo,
        @Size(min = 10, max = 2000) String mensagem
) {}
