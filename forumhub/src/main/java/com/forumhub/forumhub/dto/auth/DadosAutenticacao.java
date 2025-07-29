package com.forumhub.forumhub.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(
        @NotBlank String login,
        @NotBlank String senha
) {}

