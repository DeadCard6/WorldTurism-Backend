
---

# README — Backend (Spring Boot + SQLite)


# World Tourism Solutions — Backend

Backend del proyecto World Tourism Solutions desarrollado con Java Spring Boot.

Este repositorio contiene la lógica de negocio, autenticación, manejo de servicios turísticos y gestión de reservas para la plataforma web turística.

---

# Descripción del proyecto

World Tourism Solutions es una plataforma orientada a centralizar información turística y facilitar la planificación de viajes mediante una solución web moderna.

El backend fue desarrollado para soportar:
- Gestión de usuarios.
- Gestión de roles.
- CRUD de servicios turísticos.
- Reservas ,reseñas e itinerario.
- Administración básica del sistema.

---

# Objetivo del backend

Construir una API REST que permita conectar el frontend desarrollado en React con una base de datos centralizada para administrar usuarios, servicios y reservas.

---

# Tecnologías utilizadas

## Backend
- Java
- Spring Boot
- Spring Security
- JPA / Hibernate

## Base de datos
- SQLite

## Herramientas
- Maven
- Git
- GitHub
- Postman

---

# Arquitectura

El proyecto utiliza arquitectura cliente-servidor mediante APIs REST.

Frontend React → Backend Spring Boot → SQLite

---

# Funcionalidades implementadas

## Autenticación
- Registro de usuarios.
- Inicio de sesión.
- Gestión básica de roles.

## Usuario Turista
- Consultar servicios turísticos.
- Realizar reservas, reseñas.
- Generacion de itinerario segun reserva
- Editar perfil.

## Asesor
- Crear servicios turísticos.
- Editar servicios.
- Eliminar servicios.
- Visualizar reservas.
- Aprobar o rechazar reservas.

---

# Roles del sistema

## Usuario Turista
Usuario encargado de:
- Buscar servicios.
- Reservar actividades.
- Gestionar su perfil.

## Asesor
Usuario encargado de:
- Gestionar contenido turístico.
- Administrar reservas.
- Aprobar o rechazar solicitudes.

## Super Admin
Rol considerado para futuras funcionalidades administrativas.

---

# Requerimientos funcionales soportados

- Registro de usuarios.
- Inicio de sesión.
- Gestión de usuarios.
- CRUD de servicios.
- Gestión de reservas.
- Edición de perfil.
- Validación de permisos por roles.

---

# Aplicación de UML

El backend fue construido tomando como referencia:
- Casos de uso.
- Identificación de actores.
- Requerimientos funcionales.
- Flujo de interacción del sistema.

Esto permitió estructurar:
- Controladores.
- Servicios.
- Entidades.
- Permisos según rol.

---

# Estructura del proyecto

```bash
src/main/java/com/spring/app
 └── common/
 ├── controller/
 ├── service/
 ├── repository/
 ├── model/
 ├── view/dto
 ├── config/
 └── security/
 └── resources/
```
# Instalación y ejecución
Clonar repositorio
git clone URL_DEL_REPOSITORIO

## Configurar proyecto
VS Code.

-- Ejecutar aplicación
mvn spring-boot:run

## Base de datos

La aplicación utiliza SQLite como base de datos ligera para facilitar el desarrollo del MVP académico.

## Estado actual del proyecto

El backend fue iniciado después del frontend como parte del proceso de integración del MVP.

Actualmente soporta las funcionalidades principales necesarias para:

- Registro e inicio de sesión.
- Gestión de servicios.
- Reservas.
- Reseñas , generacion de itinerario
- Manejo de roles.

Debido al tiempo y alcance del proyecto académico, algunas funcionalidades planteadas inicialmente en los requerimientos aún se encuentran pendientes para futuras iteraciones.

## Funcionalidades futuras
Integración con WhatsApp API.
Maenejo de mapa ubicacion con Api
Dashboard administrativo completo.

# Integrantes
- Andres Felipe Loza Roa
- Johan Sebastian Maldonado Suarez
- Juan Pablo Herrera Villalba
## Asignatura

Diseño de Software

## Docente

William Aguilar

# Observaciones

Este backend corresponde al desarrollo de un MVP académico enfocado en evidenciar:

- Aplicación de UML.
- Diseño de arquitectura.
- Levantamiento de requerimientos.
- Desarrollo de APIs REST.
- Integración cliente-servidor.
