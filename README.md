FórumHub API
Status: Repositório arquivado — somente visualização (read‑only).
Código disponível para estudo. Para executar localmente, siga as instruções abaixo.

Visão geral
API REST de tópicos com Spring Boot 3, JWT, JPA/Hibernate, Flyway e MySQL 8.

Execução rápida
Com Docker

git clone https://github.com/Felipe-Maduro/forumhub.git
cd forumhub
cp .env.example .env
docker compose up --build
Aplicação em http://localhost:8080.

Local (sem Docker)
Pré‑requisitos: Java 21, Maven 3.9+, MySQL 8.
Defina as variáveis (ex.: PowerShell):
$env:SPRING_PROFILES_ACTIVE="prod"
$env:DB_HOST="127.0.0.1"
$env:DB_PORT="3306"
$env:DB_NAME="forumhub"
$env:DB_USERNAME="forumhub"
$env:DB_PASSWORD="ForumHub@2025!"
$env:JWT_SECRET="CHANGE_THIS_SECRET_WITH_32+_CHARS_MIN"
$env:ADMIN_HASH="$2a$12$zxdSS44vUMp7LDrsfjubbexDz/mhZ93jkk3t3/Dv22lYQvAeI045e"  # senha: 1234

.\mvnw.cmd clean package -DskipTests
java -jar target\forumhub-0.0.1-SNAPSHOT.jar

Autenticação
Login: POST /login
Body:{ "login": "admin", "senha": "1234" }
Resposta: { "token": "<JWT>" }
Use nas rotas protegidas: Authorization: Bearer <JWT>.

Endpoints principais
POST /topicos – criar
GET /topicos – listar
GET /topicos/{id} – detalhar
PUT /topicos/{id} – atualizar
DELETE /topicos/{id} – excluir (soft delete)

Banco & migrações
O Flyway aplica:
V1__create_tables.sql
V2__add_data_atualizacao_to_topicos.sql
V3__set_admin_password.sql — usa o placeholder ${admin_hash} (ligado à env ADMIN_HASH).

Insomnia
Coleção em insomnia/ForumHub_API.json.
Importe, execute POST /login e depois o CRUD de /topicos.
Se alterar a porta, ajuste base_url no Base Environment.

Variáveis essenciais
Defina via ambiente ou .env (compose):
DB_HOST, DB_PORT, DB_NAME, DB_USERNAME, DB_PASSWORD
JWT_SECRET (≥ 32 chars)
ADMIN_HASH (hash BCrypt da senha do admin; o .env.example traz o hash de 1234)

Teste rápido via cURL
# Login
TOKEN=$(curl -s -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"login":"admin","senha":"1234"}' | jq -r .token)

# Criar tópico
curl -X POST http://localhost:8080/topicos \
  -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" \
  -d '{ "titulo":"Primeiro tópico", "mensagem":"Conteúdo de teste" }'
Estrutura mínima:
Dockerfile
docker-compose.yml
.env.example
src/main/resources/application.properties
src/main/resources/db/migration/V1..V3.sql
insomnia/ForumHub_API.json
