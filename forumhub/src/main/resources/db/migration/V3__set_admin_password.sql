-- Define a senha do usuário admin usando placeholder do Flyway.
-- ADMIN_HASH deve ser um hash BCrypt válido.
-- Este arquivo é idempotente: sempre sobrescreve a senha do admin.

UPDATE usuarios
   SET senha = '${$2a$12$c4OlP3ITJJr6voCx7wj6meJohnRWAheLRFJVeab6TJuzGyEG0.Ubu}'
 WHERE login = 'admin1';
