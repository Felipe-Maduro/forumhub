package com.forumhub.forumhub.dto.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosCadastroTopico(
        @NotBlank @Size(min = 5, max = 100) String titulo,
        @NotBlank @Size(min = 10, max = 2000) String mensagem
) {}

