## Foro Hub

Foro Hub es una aplicación web desarrollada con Spring Boot y MySQL, que permite crear y visualizar tópicos de discusión en un foro. Incluye autenticación de usuarios y manejo de sesiones mediante Spring Security.

## 🛠 Tecnologías utilizadas

- Java 17
- Spring Boot
  - Spring Web
  - Spring Data JPA
  - Spring Security
- MySQL (gestión de base de datos)
- Postman (para probar APIs)
- Flyway (para migraciones de base de datos)

## ⚡ Funcionalidades

- Registro e inicio de sesión de usuarios (JWT con Spring Security)
- Creación de tópicos en el foro
- Visualización de tópicos existentes
- Persistencia de datos con MySQL
- Scripts de migración con Flyway

## 📝 Uso

- Crear un tópico: enviar un POST a /topicos con los campos titulo, mensaje, autor, curso.
- Listar tópicos: enviar un GET a /topicos.
Puedes usar Postman para probar todas las APIs de manera rápida.

## 🔒 Seguridad

- Contraseñas almacenadas con BCrypt.
- Autenticación y autorización mediante Spring Security.
- Endpoints protegidos según roles de usuario.

## Autora

Marcela Aros
