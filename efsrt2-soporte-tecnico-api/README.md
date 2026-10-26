# Sistema de Soporte Técnico - Backend

API REST desarrollada para la gestión de solicitudes de soporte técnico.

<<<<<<< HEAD
## Descripción

El proyecto permite registrar, consultar, actualizar y eliminar solicitudes de soporte técnico mediante una API REST.

También permite administrar clientes y técnicos, aplicar validaciones, controlar errores y consultar solicitudes según su estado o prioridad.

La información se almacena temporalmente en memoria utilizando estructuras `Map`, por lo que los datos se eliminan cuando se reinicia la aplicación.
=======
El sistema permite administrar clientes, técnicos y solicitudes, aplicando reglas de negocio para la asignación de técnicos, prioridades y cambios de estado.
>>>>>>> 75dfe36 (Integra persistencia MySQL, reglas de negocio y arquitectura por capas)

## Integrantes y responsabilidades

| Integrante | Nombre | Responsabilidad |
|---|---|---|
| Integrante 1 | Tacas Rocha, Jeymi Michael | Desarrollo de controladores, manejo de excepciones, documentación y pruebas con Postman |
| Integrante 2 | Hernandez Ramirez, Aldair Davis | Desarrollo de modelos y DTO, apoyo en validaciones y pruebas funcionales |
| Integrante 3 | Rosmery Espinoza Quito | Desarrollo de servicios e implementaciones de la lógica de negocio |

## Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Hibernate
- MySQL
- Jakarta Validation
- Maven
- Swagger / OpenAPI

## Arquitectura

El backend está organizado mediante separación de responsabilidades:

```text
Controller
    ↓
Application Service
    ↓
Service
    ↓
Validator
    ↓
Repository
    ↓
JPA / Hibernate
    ↓
MySQL
```

Principales paquetes:

```text
src/main/java/com/idat/soportetecnico
├── application
├── controller
├── dto
├── exception
├── mapper
├── model
├── repository
├── service
└── validator
```

<<<<<<< HEAD
Además, en la raíz del proyecto se encuentran las evidencias y la colección de pruebas:

```text
soporte-tecnico-api
├── docs
│   └── capturas_soporte_tecnico
├── postman
│   └── Soporte-Tecnico-API.postman_collection.json
├── src
├── pom.xml
└── README.md
```

## Funcionalidades
=======
## Base de datos
>>>>>>> 75dfe36 (Integra persistencia MySQL, reglas de negocio y arquitectura por capas)

El proyecto utiliza MySQL.

Nombre de la base de datos:

```text
soporte_tecnico_db
```

El script para crear la base de datos, las tablas y los datos iniciales se encuentra en:

```text
database/setup.sql
```

Antes de ejecutar la aplicación se debe ejecutar dicho script en MySQL.

## Configuración

<<<<<<< HEAD
- `@Valid`
- `@NotBlank`
- `@NotNull`
- `@Size`
- `@Email`
- `@Positive`

Cuando los datos enviados no cumplen las reglas establecidas, la API devuelve:

```text
400 Bad Request
```

## Manejo de errores

El proyecto utiliza `GlobalExceptionHandler` mediante `@RestControllerAdvice`.

Los principales códigos manejados son:

```text
400 Bad Request → datos inválidos o incumplimiento de una regla
404 Not Found   → recurso no encontrado
```

Por ejemplo, consultar un cliente inexistente puede devolver:

```json
{
  "fecha": "2026-10-24T00:00:00",
  "estado": 404,
  "mensaje": "Cliente no encontrado",
  "error": "Not Found"
}
```

## Endpoints principales

### Clientes
=======
La conexión se configura en:

```text
src/main/resources/application.properties
```

La contraseña de MySQL no se almacena directamente en el proyecto.

Se utiliza la variable de entorno:
>>>>>>> 75dfe36 (Integra persistencia MySQL, reglas de negocio y arquitectura por capas)

```text
DB_PASSWORD
```

Ejemplo en PowerShell:

```powershell
$env:DB_PASSWORD="root"
```

El usuario de MySQL puede configurarse mediante:

```text
DB_USER
```

<<<<<<< HEAD
### Solicitudes

```text
POST   /api/solicitudes
GET    /api/solicitudes
GET    /api/solicitudes/{id}
PUT    /api/solicitudes/{id}
DELETE /api/solicitudes/{id}

GET    /api/solicitudes/estado/{estado}
GET    /api/solicitudes/prioridad/{prioridad}
```

## Ejemplos de uso de la API

### Registrar un cliente

**POST**

```text
/api/clientes
```

Petición:

```json
{
  "nombre": "Juan Cruz",
  "email": "juan@gmail.com",
  "empresa": "Empresa ABC"
}
```

Respuesta esperada:

```json
{
  "id": 1,
  "nombre": "Juan Cruz",
  "email": "juan@gmail.com",
  "empresa": "Empresa ABC"
}
```

Código esperado:

```text
201 Created
```

### Registrar un técnico

**POST**

```text
/api/tecnicos
```

Petición:

```json
{
  "nombre": "Carlos Mendoza",
  "especialidad": "Soporte y Redes",
  "disponible": true
}
```

Respuesta esperada:

```json
{
  "id": 1,
  "nombre": "Carlos Mendoza",
  "especialidad": "Soporte y Redes",
  "disponible": true
}
```

Código esperado:

```text
201 Created
```

### Registrar una solicitud

**POST**

```text
/api/solicitudes
```

Petición:

```json
{
  "titulo": "Problema con Outlook",
  "descripcion": "El usuario no puede ingresar a su correo corporativo",
  "prioridad": "ALTA",
  "clienteId": 1,
  "tecnicoId": 1
}
```

Respuesta esperada:

```json
{
  "id": 1,
  "titulo": "Problema con Outlook",
  "descripcion": "El usuario no puede ingresar a su correo corporativo",
  "prioridad": "ALTA",
  "estado": "PENDIENTE",
  "clienteId": 1,
  "clienteNombre": "Juan Cruz",
  "tecnicoId": 1,
  "tecnicoNombre": "Carlos Mendoza"
}
```

Código esperado:

```text
201 Created
```

### Actualizar el estado de una solicitud

**PUT**

```text
/api/solicitudes/1
```

Petición:

```json
{
  "titulo": "Problema con Outlook",
  "descripcion": "El usuario continúa sin poder ingresar a su correo corporativo",
  "prioridad": "ALTA",
  "estado": "EN_PROCESO",
  "clienteId": 1,
  "tecnicoId": 1
}
```

Código esperado:

```text
200 OK
```

### Eliminar una solicitud

**DELETE**

```text
/api/solicitudes/1
```

Código esperado:

```text
204 No Content
```

## Swagger / OpenAPI

La API cuenta con documentación interactiva mediante Swagger.

Con la aplicación ejecutándose, Swagger se encuentra disponible en:

```text
http://localhost:8080/swagger-ui.html
```

La especificación OpenAPI se encuentra disponible en:

```text
http://localhost:8080/v3/api-docs
```

Swagger permite visualizar y probar directamente los endpoints REST.
=======
Si no se define, se utiliza `root` como valor predeterminado.
>>>>>>> 75dfe36 (Integra persistencia MySQL, reglas de negocio y arquitectura por capas)

## Ejecución del proyecto

<<<<<<< HEAD
### Requisitos

- Java 21.
- Maven o Maven Wrapper.
- IntelliJ IDEA, Eclipse, VS Code u otro IDE compatible.

### Clonar el repositorio

```bash
git clone https://github.com/MichaelTacas/soporte-tecnico-api.git
```

Ingresar al proyecto:

```bash
cd soporte-tecnico-api
```

### Ejecutar en Windows

```bash
.\mvnw.cmd spring-boot:run
```

### Ejecutar en Linux o macOS

```bash
./mvnw spring-boot:run
```

También puede ejecutarse directamente la clase:
=======
Desde la carpeta del backend:
>>>>>>> 75dfe36 (Integra persistencia MySQL, reglas de negocio y arquitectura por capas)

```powershell
$env:DB_PASSWORD="root"
.\mvnw.cmd spring-boot:run
```

La API se ejecuta por defecto en:

```text
http://localhost:8080
```

## Swagger

Con el backend iniciado, la documentación de los endpoints puede consultarse en:

```text
http://localhost:8080/swagger-ui/index.html
```

## Funcionalidades principales

### Clientes

- Registrar clientes.
- Listar clientes.
- Buscar clientes por ID.
- Actualizar clientes.
- Eliminar clientes.

### Técnicos

- Registrar técnicos.
- Listar técnicos.
- Buscar técnicos por ID.
- Actualizar técnicos.
- Eliminar técnicos.
- Consultar técnicos disponibles.

### Solicitudes

- Registrar solicitudes.
- Listar solicitudes.
- Buscar solicitudes por ID.
- Actualizar solicitudes.
- Eliminar solicitudes.
- Filtrar solicitudes por prioridad.
- Filtrar solicitudes por estado.
- Asignar técnicos.
- Cambiar el estado de una solicitud.

## Reglas de negocio

Al registrar una solicitud se asigna inicialmente el estado:

```text
PENDIENTE
```

Los estados de una solicitud siguen el flujo:

```text
PENDIENTE
    ↓
EN_PROCESO
    ↓
RESUELTA
    ↓
CERRADA
```

El sistema valida las transiciones mediante:

```text
TransicionEstadoValidator
```

También se valida que un técnico se encuentre disponible mediante:

```text
TecnicoDisponibleValidator
```

Esto permite cumplir con el requerimiento del proyecto de simular el almacenamiento utilizando estructuras en memoria.

## Pruebas

Las pruebas pueden ejecutarse con:

<<<<<<< HEAD
- Postman.
- Swagger UI.

Se verificaron:

- Operaciones CRUD.
- Registro de clientes.
- Registro de técnicos.
- Registro de solicitudes.
- Consultas por ID.
- Actualización de registros.
- Eliminación de registros.
- Validaciones.
- Respuestas `200 OK`.
- Respuestas `201 Created`.
- Respuestas `204 No Content`.
- Errores `400 Bad Request`.
- Errores `404 Not Found`.
- Cambio de estado de solicitudes.
- Consulta por estado.
- Consulta por prioridad.
- Técnicos disponibles.

## Colección de Postman

El proyecto incluye una colección completa de Postman:

```text
postman/Soporte-Tecnico-API.postman_collection.json
```

La colección puede importarse directamente desde Postman mediante:

```text
Import
→ Files
→ Soporte-Tecnico-API.postman_collection.json
```

La colección está organizada en:

```text
01 - Preparación de datos
02 - Clientes
03 - Técnicos
04 - Solicitudes
05 - Eliminaciones
```

La carpeta `01 - Preparación de datos` permite volver a registrar los datos necesarios después de reiniciar Spring Boot.

La colección utiliza variables para almacenar automáticamente:

```text
baseUrl
clienteId
tecnicoId
solicitudId
```

También contiene pruebas automáticas para verificar códigos de respuesta como:

```text
200 OK
201 Created
204 No Content
400 Bad Request
404 Not Found
```

## Evidencias

Las capturas de las pruebas funcionales realizadas durante el desarrollo se encuentran en:

```text
docs/capturas_soporte_tecnico/
```

Entre las evidencias se incluyen:

- Registro de cliente.
- Listado de clientes.
- Error 404 de cliente inexistente.
- Validaciones de cliente.
- Registro de técnico.
- Validaciones de técnico.
- Registro de solicitud.
- Actualización del estado de una solicitud.
- Ejecución de Spring Boot.
- Swagger / OpenAPI.
- Compilación correcta con Maven.

## Principios de diseño

El proyecto utiliza separación de responsabilidades mediante capas e interfaces.

El flujo principal es:

```text
Cliente HTTP / Postman / Swagger
            ↓
       Controller
            ↓
         Service
            ↓
       ServiceImpl
            ↓
       Map en memoria
```

Cada componente tiene una responsabilidad específica.

Los controladores reciben las peticiones HTTP y delegan la lógica a las interfaces de servicio.

Las implementaciones de los servicios contienen la lógica correspondiente y gestionan los datos almacenados en memoria.

Esto permite reducir el acoplamiento y facilita futuras modificaciones o ampliaciones del proyecto.
=======
```powershell
.\mvnw.cmd test
```

Actualmente el proyecto cuenta con la prueba de carga del contexto de Spring Boot (`contextLoads`).
>>>>>>> 75dfe36 (Integra persistencia MySQL, reglas de negocio y arquitectura por capas)

Resultado validado:

```text
Tests run: 1
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

<<<<<<< HEAD
Las funcionalidades desarrolladas en cada rama son posteriormente integradas a la rama principal `main`.

Repositorio del proyecto:

```text
https://github.com/MichaelTacas/soporte-tecnico-api
```

## Resultado

El proyecto implementa una API REST funcional para administrar clientes, técnicos y solicitudes de soporte técnico.

La solución incluye arquitectura por capas, operaciones CRUD, validaciones, manejo centralizado de errores, documentación mediante Swagger, pruebas funcionales con Postman y almacenamiento temporal en memoria.
=======
Como mejora futura se implementarán pruebas unitarias específicas para servicios y validadores mediante JUnit y Mockito.

## Seguridad

Actualmente la API no implementa autenticación.

Como mejora futura se propone incorporar:

- Spring Security.
- Autenticación mediante JWT.
- Roles y permisos.
- BCrypt para el almacenamiento seguro de contraseñas.

## Frontend

El frontend del sistema se encuentra desarrollado de manera independiente utilizando React, TypeScript y Vite.

El frontend consume los endpoints REST proporcionados por esta API.
>>>>>>> 75dfe36 (Integra persistencia MySQL, reglas de negocio y arquitectura por capas)
