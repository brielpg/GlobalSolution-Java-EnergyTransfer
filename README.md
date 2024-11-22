
# Energy Transfer

## Integrantes
- RM552798 Gabriel Fossatti Beltran  
- RM554012 Gabriel Pescarolli Galiza  
- RM554258 Guilherme Gambarão Baptista

## 1. Descrição do Projeto
Nosso projeto propõe uma solução para garantir energia de forma sustentável e acessível para todos. A ideia é criar
um aplicativo onde os usuários podem vender e comprar energia de forma simples e direta, como se fosse um mercado
para energia, onde o usuário publica um anúncio e outro usuário pode adquiri-lo.

Essa ideia incentiva que pessoas que produzam energia (por exemplo, painéis solares) possam vender o excedente para
quem precisa, contribuindo para preservação do ambiente e o crescimento econômico sustentável.

### Java

Nossa aplicação consiste em uma API desenvolvida em Java utilizando o framework Spring Boot para venda de energia. A API permite a criação, atualização, autenticação, listagem e delete de usuários; Além de criação, compra, atualização e listagem de anúncios.
Este sistema permite gerenciar:

- **Usuários**: Criação, autenticação, atualização, listagem e desativação.
- **Anúncios**: Criação, compra, atualização e listagem.
- **Endereços**: Associação e atualização de informações de endereço de usuários.

O Json com os testes do **POSTMAN** está na raiz projeto.  
O projeto segue os padrões REST e é documentado com **Swagger**.

### Tecnologias Utilizadas
- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Web**
- **Hibernate**
- **Swagger**
- **Banco de Dados Oracle**
- **Maven**

## 2. Relacionamentos:

**1. Usuario com Endereço**  
_**Relacionamento:**_ Many to One  
_**Descrição:**_ Cada usuário está associado a um endereço, mas um endereço pode ser compartilhado por vários usuários.

**2. Anuncio com Usuário**  
_**Relacionamento:**_ Many to One  
_**Descrição:**_ Um anúncio tem um vendedor (usuário) e, caso seja comprado, também está relacionado a um comprador (outro usuário).

## 3. Vídeos
PITCH: https://youtu.be/hXPGfkWNVXA  
Funcionamento da API: https://youtu.be/CGQLfBjFlLY

## 4. Endpoints Disponíveis

### 4.1. Endpoint de Usuários - /usuario
**POST** /usuario - cria um novo usuário  
**POST** /usuario/login - login do usuário  
**PUT**  /usuario - atualiza informacoes de um usuário  
**GET**  /usuario - lista todos os usuários  
**GET**  /usuario/cpf - lista as informacoes de um usuário específico pelo seu cpf  
**DEL**  /usuario/id - faz um delete lógico de um usuário através do id fornecido

### 4.2. Endpoint de Anúncios - /anuncio
**POST** /anuncio - cria um novo anúncio  
**POST** /anuncio/comprar - realiza a compra de um anúncio  
**PUT** /anuncio - atualiza informacoes do anúncio pelo id  
**GET** /anuncio - lista todos anúncios  
**GET** /anuncio/id - lista as informacoes de um anúncio específico pelo seu id