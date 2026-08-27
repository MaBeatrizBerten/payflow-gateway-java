# PayFlow Gateway

O PayFlow Gateway é uma API para praticar desenvolvimento backend no domínio de pagamentos. Nesta fase, o repositório foi migrado para Java com Spring Boot, oferecendo uma base funcional de treinamento; ele não processa pagamentos reais.

## Stack

* Java 21+
* Spring Boot (Web, Data JPA, Validation)
* PostgreSQL 17 via Docker Compose
* JUnit 5, Mockito e MockMvc para testes

## Requisitos

* JDK 21 ou superior instalado
* Maven para o gerenciamento de dependências
* Docker com Docker Compose para os exercícios de banco de dados
* Configuração local baseada no `application.properties`

## Scripts / Comandos Maven

| Comando | Finalidade |
| :--- | :--- |
| `mvn spring-boot:run` | inicia a API em modo de desenvolvimento |
| `mvn clean compile` | compila o projeto Java |
| `mvn clean package` | gera o arquivo executável (JAR) |
| `mvn test` | executa os testes unitários e de integração |
