# 🧩Sistema de Microsserviços com Spring Boot e Docker e Kubernetes

Este projeto demonstra a construção de uma arquitetura baseada em microsserviços usando Java com Spring Boot, Docker e PostgreSQL. Ele foi desenvolvido com base nos conceitos abordados Back-end Java Microsserviços, Spring Boot e Kubernetes.

## 🚀Objetivo
Desenvolver uma aplicação backend dividida em três microsserviços:
- user-api: gerenciamento de usuários
- product-api: gerenciamento de produtos
- shopping-api: gerenciamento de compras

Cada microsserviço é implementado com Spring Boot, utiliza comunicação REST e possui autenticação básica. O projeto é containerizado com Docker.

## 📚 Tecnologias Utilizadas
- Java 11+
- Spring Boot 2.3
- Spring Data JPA
- Spring Cloud Config (para configuração centralizada)
- Docker (contêineres e imagens)
- PostgreSQL
- PGAdmin
- Postman (para testes de APIs)

## 🛠️ Como Executar Localmente
### Pré-requisitos
- Docker e Docker Compose instalados
- Maven instalado (ou uso do Maven Wrapper)
- JDK configurado

## 📦 Estrutura dos Microsserviços
Cada serviço segue o padrão MVC com as camadas:
- Controller: exposição das APIs REST
- Service: regras de negócio
- Repository: acesso a dados com Spring Data
- DTO: transporte de dados entre camadas e entre APIs
