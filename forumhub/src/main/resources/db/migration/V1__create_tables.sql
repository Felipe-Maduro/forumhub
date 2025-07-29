-- ===========================================
-- FórumHub - Migração V1
-- Criação de tabelas básicas e usuário admin
-- ===========================================

-- Recomendado para garantir consistência de acentuação
SET NAMES utf8mb4;
SET time_zone = '+00:00';

-- -----------------------
-- Tabela: usuarios
-- -----------------------
CREATE TABLE IF NOT EXISTS usuarios (
    id     BIGINT NOT NULL AUTO_INCREMENT,
    login  VARCHAR(255) NOT NULL UNIQUE,
    senha  VARCHAR(255) NOT NULL,
    role   ENUM('ROLE_USER','ROLE_ADMIN') NOT NULL DEFAULT 'ROLE_USER',
    ativo  TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -----------------------
-- Tabela: topicos
-- -----------------------
CREATE TABLE IF NOT EXISTS topicos (
    id            BIGINT NOT NULL AUTO_INCREMENT,
    titulo        VARCHAR(255) NOT NULL,
    mensagem      TEXT NOT NULL,
    data_criacao  DATETIME NOT NULL,
    status        ENUM('ABERTO','FECHADO') NOT NULL DEFAULT 'ABERTO',
    ativo         TINYINT(1) NOT NULL DEFAULT 1,
    autor_id      BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_topicos_autor
        FOREIGN KEY (autor_id) REFERENCES usuarios (id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Índices úteis
CREATE INDEX idx_topicos_autor   ON topicos (autor_id);
CREATE INDEX idx_topicos_status  ON topicos (status);
CREATE INDEX idx_topicos_ativo   ON topicos (ativo);

-- ===========================================
-- Usuário administrador inicial
-- Troque a senha posteriormente.
-- Senha em texto: 1234
-- Hash BCrypt (custo 10):
-- $2a$10$7EqJtq98hPqEX7fNZaFWoO5rG4C.b..rJ1ZVQ884w3jH0hZ6P6W5e
-- ===========================================
INSERT INTO usuarios (login, senha, role, ativo)
VALUES (
    'admin',
    '$2a$12$ZxdSS44vUMp7LDrsfjubbexDz/mhZ93jkk3t3/Dv22lYQvAeI045e',
    'ROLE_ADMIN',
    1
)
ON DUPLICATE KEY UPDATE
    login = VALUES(login);
