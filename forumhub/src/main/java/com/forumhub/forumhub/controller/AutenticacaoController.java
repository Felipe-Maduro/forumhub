package com.forumhub.forumhub.controller;

import com.forumhub.forumhub.dto.auth.DadosAutenticacao;
import com.forumhub.forumhub.dto.auth.DadosTokenJWT;
import com.forumhub.forumhub.security.jwt.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid DadosAutenticacao dados) {
        var credentials = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var auth = authenticationManager.authenticate(credentials);
        var token = tokenService.generateToken(auth.getName());
        return ResponseEntity.ok(new DadosTokenJWT(token));
    }
}
