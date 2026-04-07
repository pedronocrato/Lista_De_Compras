🛒 Lista de Compras - API REST com Spring Boot

Este projeto é uma aplicação backend desenvolvida com Java 17 e Spring Boot 3, com o objetivo de permitir que usuários criem listas de compras personalizadas, adicionem produtos reais consultados em uma API externa, e acompanhem os preços e links dos itens em tempo real. A aplicação utiliza arquitetura em camadas, integração com banco de dados relacional MySQL via Docker e consumo de API pública de produtos (DummyJSON).

---

## ✅ Funcionalidades

- ✅ Cadastro de usuários (name, email, password)
- ✅ Criação de listas de compras associadas a um usuário
- ✅ Adição de itens com dados vindos de uma API de produtos (nome, preço, link)
- ✅ Cálculo automático do valor total da lista de compras
- ✅ Persistência dos dados com JPA e Hibernate
- ✅ Integração com banco de dados MySQL via Docker Compose

---

## 🧱 Tecnologias e Ferramentas

- Java 17
- Spring Boot 3
- Spring Data JPA
- Hibernate
- Maven
- MySQL(Docker)
- Lombok
- RestTemplate (consumo de API)
- DTOs (record)
- Docker e Docker Compose

---

## 🔌 Integração com API Externa

Os itens adicionados às listas de compras são buscados em tempo real a partir da API pública [DummyJSON](https://dummyjson.com/products), que fornece dados simulados como:

- Título do produto
- Preço
- Link (permalink)

Essa estratégia evita a manutenção de um catálogo local de produtos e permite trabalhar com dados realistas.

📌 Padrões e Boas Práticas

✅ Uso de DTOs (record) para abstração de entrada e saída de dados, evitando expor diretamente as entidades JPA na API pública.

✅ Separação clara de responsabilidades seguindo o padrão MVC:
Camadas distintas para Controller (entrada), Service (lógica de negócio), Repository (acesso a dados) e Model (entidades JPA).

✅ Validação e tratamento de exceções, como EntityNotFoundException, para garantir respostas adequadas e evitar erros inesperados na API.

✅ Uso de UUID como identificadores únicos e universais nas entidades, aumentando a segurança e a escalabilidade.

✅ Cálculo automático do valor total da lista com base na quantidade e no preço dos itens adicionados — refletindo sempre o valor atualizado conforme inserções ou remoções.

✅ Versionamento seguro com .gitignore configurado para proteger dados sensíveis (como application.properties, .env e docker-compose.yaml) e evitar arquivos desnecessários no repositório (como target/, .idea/, logs etc).

✅ Uso de Docker e Docker Compose para isolar o ambiente de banco de dados, garantindo consistência no desenvolvimento e facilidade de setup em qualquer máquina com um simples docker-compose up. Isso elimina problemas de dependências e facilita o deploy.
