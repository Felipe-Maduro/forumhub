package com.forumhub.forumhub.model;

import com.forumhub.forumhub.topico.StatusTopico;
import com.forumhub.forumhub.user.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "topicos")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    @NotBlank(message = "Título não pode ser vazio")
    @Size(min = 5, max = 100, message = "Título deve ter entre 5 e 100 caracteres")
    private String titulo;

    @Column(name = "mensagem", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Mensagem não pode ser vazia")
    @Size(min = 10, max = 2000, message = "Mensagem deve ter entre 10 e 2000 caracteres")
    private String mensagem;

    @CreatedDate
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusTopico status;

    @Column(nullable = false)
    private boolean ativo;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @PrePersist
    void prePersist() {
        if (status == null) status = StatusTopico.ABERTO;
        ativo = true;
    }

    public void atualizar(String novoTitulo, String novaMensagem) {
        if (novoTitulo != null && !novoTitulo.isBlank()) this.titulo = novoTitulo;
        if (novaMensagem != null && !novaMensagem.isBlank()) this.mensagem = novaMensagem;
    }

    public void desativar() { this.ativo = false; }
}
