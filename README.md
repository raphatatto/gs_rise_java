# GS Rise (Spring Boot)

Projeto Java Spring Boot para a solução GS, com stack incluindo Spring Web, Security, Data JPA, Thymeleaf, Swagger, RabbitMQ e integração com SQL Server e Flyway. Use os espaços abaixo para registrar os links de entrega e os integrantes da equipe.

## Links da Entrega
- **Link 1:** [Link do video](https://youtu.be/4if-_zztZh4)
- **Link 2:** <!-- insira aqui -->

## Integrantes
- **Nome e RM 1:** 554983 - Raphaela Oliviera Tatto
- **Nome e RM 2:** 558021 - Tiago Ribeiro Capela

## Requisitos
- Java 17+
- Maven
- Banco SQL Server acessível (ajuste `application.properties` conforme necessário)

## Como executar
1. Instale as dependências: `./mvnw dependency:resolve`
2. Execute a aplicação: `./mvnw spring-boot:run`
3. A documentação OpenAPI estará disponível em `http://locahost:8080` após a aplicação subir.
4. A aplicação web OpenAPI estará disponível em `/app` após a aplicação subir.

## Testes
- Rode a suíte automatizada com: `./mvnw test`
