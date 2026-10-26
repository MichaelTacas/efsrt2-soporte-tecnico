# Sistema de Soporte Técnico - Backend

API REST desarrollada para la gestión de solicitudes de soporte técnico.

El sistema permite administrar clientes, técnicos y solicitudes de soporte, aplicando reglas de negocio para la asignación de técnicos, prioridades y cambios de estado.

La información se almacena de forma persistente en una base de datos MySQL mediante Spring Data JPA e Hibernate.

## Integrantes y responsabilidades

| Integrante | Nombre | Responsabilidad |
|---|---|---|
| Integrante 1 | Tacas Rocha, Jeymi Michael | Desarrollo de controladores, manejo de excepciones, documentación y pruebas |
| Integrante 2 | Hernandez Ramirez, Aldair Davis | Desarrollo de modelos y DTO, apoyo en validaciones y pruebas funcionales |
| Integrante 3 | Rosmery Espinoza Quito | Desarrollo de servicios e implementación de lógica de negocio |

## Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Hibernate
- MySQL
- Jakarta Validation
- Maven
- JUnit
- Swagger / OpenAPI

## Arquitectura

El backend se encuentra organizado mediante una arquitectura por capas y separación de responsabilidades.

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
│   └── impl
├── controller
├── dto
├── exception
├── mapper
├── model
├── repository
├── service
│   └── impl
└── validator
```

Cada capa cumple una responsabilidad específica:

- **Controller:** recibe las solicitudes HTTP.
- **Application Service:** coordina los casos de uso.
- **Service:** contiene la lógica relacionada con cada entidad.
- **Validator:** aplica reglas de negocio.
- **Repository:** gestiona el acceso a los datos.
- **JPA / Hibernate:** realiza el mapeo objeto-relacional.
- **MySQL:** almacena la información de manera persistente.

## Base de datos

El proyecto utiliza MySQL.

Nombre de la base de datos:

```text
soporte_tecnico_db
```

El script para crear la base de datos, tablas y datos iniciales se encuentra en la carpeta raíz del proyecto completo:

```text
database/setup.sql
```

La estructura general es:

```text
efsrt2-soporte-tecnico
├── database
│   └── setup.sql
├── efsrt2-soporte-tecnico-api
└── efsrt2-soporte-tecnico-web
```

Antes de ejecutar el sistema se recomienda ejecutar `database/setup.sql` desde MySQL Workbench.

## Configuración

La conexión a MySQL se configura en:

```text
src/main/resources/application.properties
```

La contraseña de MySQL no se almacena directamente en el repositorio.

Se utiliza la variable de entorno:

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

Si no se define, se utiliza `root` como valor predeterminado.

## Validaciones

El proyecto utiliza Jakarta Validation para validar los datos recibidos por la API.

Entre las anotaciones utilizadas se encuentran:

```text
@Valid
@NotBlank
@NotNull
@Size
@Email
@Positive
```

Cuando una petición contiene datos inválidos, la API puede responder con:

```text
400 Bad Request
```

## Manejo de errores

El proyecto cuenta con un manejador global de excepciones mediante:

```text
GlobalExceptionHandler
```

utilizando:

```text
@RestControllerAdvice
```

Los principales códigos manejados son:

```text
400 Bad Request → datos inválidos o incumplimiento de una regla de negocio
404 Not Found   → recurso solicitado no encontrado
```

También se utilizan excepciones personalizadas como:

```text
RecursoNoEncontradoException
ReglaNegocioException
```

Esto permite centralizar el manejo de errores y evitar repetir esta lógica dentro de los controladores.

## Funcionalidades principales

### Clientes

El backend permite:

- Registrar clientes.
- Listar clientes.
- Buscar clientes por ID.
- Actualizar clientes.
- Eliminar clientes.

### Técnicos

El backend permite:

- Registrar técnicos.
- Listar técnicos.
- Buscar técnicos por ID.
- Actualizar técnicos.
- Eliminar técnicos.
- Consultar técnicos disponibles.

### Solicitudes

El backend permite:

- Registrar solicitudes.
- Listar solicitudes.
- Buscar solicitudes por ID.
- Actualizar solicitudes.
- Eliminar solicitudes.
- Consultar solicitudes por prioridad.
- Consultar solicitudes por estado.
- Asignar técnicos.
- Cambiar el estado de una solicitud.

## Endpoints principales

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

Los controladores de clientes y técnicos proporcionan operaciones CRUD equivalentes para sus respectivas entidades.

## Ejemplo de registro de una solicitud

### Petición

```http
POST /api/solicitudes
```

```json
{
  "titulo": "Problema con Outlook",
  "descripcion": "El usuario no puede ingresar a su correo corporativo",
  "prioridad": "ALTA",
  "clienteId": 1,
  "tecnicoId": 1
}
```

### Respuesta esperada

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

## Reglas de negocio

Al registrar una nueva solicitud, el sistema utiliza inicialmente el estado:

```text
PENDIENTE
```

El flujo permitido es:

```text
PENDIENTE
    ↓
EN_PROCESO
    ↓
RESUELTA
    ↓
CERRADA
```

### Validación de transiciones

La clase:

```text
TransicionEstadoValidator
```

se encarga de verificar que una solicitud no cambie directamente hacia un estado que no corresponda.

Ejemplo permitido:

```text
PENDIENTE → EN_PROCESO
```

Ejemplo rechazado:

```text
PENDIENTE → CERRADA
```

### Validación de técnicos

La clase:

```text
TecnicoDisponibleValidator
```

verifica que un técnico se encuentre disponible antes de ser asignado a una solicitud.

También se permite registrar una solicitud sin técnico asignado cuando corresponda.

## Swagger / OpenAPI

La API cuenta con documentación interactiva mediante Swagger.

Con el backend iniciado se puede acceder a:

```text
http://localhost:8080/swagger-ui/index.html
```

La especificación OpenAPI se encuentra disponible en:

```text
http://localhost:8080/v3/api-docs
```

Swagger permite consultar y probar los endpoints REST directamente desde el navegador.

## Ejecución del proyecto

### Requisitos

Se necesita:

- Java 21.
- Maven o Maven Wrapper.
- MySQL.
- IntelliJ IDEA, Eclipse, VS Code u otro IDE compatible.

### Ejecutar en Windows

Desde:

```text
efsrt2-soporte-tecnico-api
```

configurar la contraseña de MySQL:

```powershell
$env:DB_PASSWORD="root"
```

Luego ejecutar:

```powershell
.\mvnw.cmd spring-boot:run
```

### Ejecutar en Linux o macOS

```bash
export DB_PASSWORD="TU_CONTRASEÑA"
./mvnw spring-boot:run
```

La API se ejecuta por defecto en:

```text
http://localhost:8080
```

## Pruebas automatizadas

El backend cuenta con pruebas automatizadas desarrolladas con JUnit.

Actualmente se ejecutan **8 pruebas**:

- 1 prueba de carga del contexto de Spring Boot.
- 3 pruebas relacionadas con la disponibilidad de técnicos.
- 4 pruebas relacionadas con las transiciones de estado de las solicitudes.

### TecnicoDisponibleValidatorTest

Comprueba reglas como:

```text
Técnico disponible
→ permite registrar la solicitud

Técnico no disponible
→ lanza ReglaNegocioException

Solicitud sin técnico
→ permite registrar la solicitud
```

### TransicionEstadoValidatorTest

Comprueba las siguientes transiciones:

```text
PENDIENTE → EN_PROCESO ✅
EN_PROCESO → RESUELTA ✅
RESUELTA → CERRADA ✅
PENDIENTE → CERRADA ❌
```

Las pruebas pueden ejecutarse mediante:

```powershell
.\mvnw.cmd test
```

Resultado verificado:

```text
Tests run: 8
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

Estas pruebas permiten comprobar automáticamente que las principales reglas de negocio continúan funcionando después de realizar cambios en el sistema.

## Pruebas funcionales

Además de las pruebas unitarias, durante el desarrollo se realizaron pruebas funcionales utilizando:

- Swagger UI.
- Postman.
- Navegador web mediante la integración con el frontend.

Se verificaron operaciones como:

- Registro de clientes.
- Registro de técnicos.
- Registro de solicitudes.
- Consultas.
- Actualizaciones.
- Eliminaciones.
- Validaciones.
- Cambio de estados.
- Consulta por prioridad.
- Consulta por estado.
- Consulta de técnicos disponibles.
- Manejo de errores.

## Principios de diseño

El proyecto utiliza separación de responsabilidades y principios SOLID para evitar concentrar toda la lógica en una sola clase.

El flujo principal del backend es:

```text
Cliente HTTP / Frontend
        ↓
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

Algunos ejemplos de separación de responsabilidades son:

```text
SolicitudController
→ atiende las peticiones HTTP

SolicitudApplicationService
→ coordina el caso de uso

SolicitudService
→ ejecuta operaciones de negocio

Validators
→ verifican reglas específicas

SolicitudRepository
→ gestiona la persistencia

SolicitudMapper
→ transforma entidades y DTO
```

Esta estructura permite mantener un bajo acoplamiento y facilita la ampliación y mantenimiento del sistema.

## Seguridad

Actualmente la API no implementa un sistema completo de autenticación y autorización.

Como mejora futura se propone incorporar:

- Spring Security.
- Autenticación mediante JWT.
- Roles y permisos.
- BCrypt para el almacenamiento seguro de contraseñas.

Las credenciales locales de MySQL no se almacenan directamente en el código fuente y se configuran mediante variables de entorno.

## Frontend

El frontend se encuentra desarrollado utilizando:

- React.
- TypeScript.
- Vite.

Se encuentra dentro del mismo proyecto general:

```text
efsrt2-soporte-tecnico-web
```

El frontend consume los endpoints REST proporcionados por este backend.

El flujo completo es:

```text
Usuario
   ↓
React + TypeScript
   ↓
API REST Spring Boot
   ↓
Lógica de negocio
   ↓
JPA / Hibernate
   ↓
MySQL
```

## Repositorio

El backend forma parte del repositorio general del proyecto:

```text
https://github.com/MichaelTacas/efsrt2-soporte-tecnico
```

Dentro del repositorio se encuentran:

```text
database/
efsrt2-soporte-tecnico-api/
efsrt2-soporte-tecnico-web/
```

Esto permite mantener en un mismo repositorio la capa de datos, la lógica de negocio y la vista.

## Resultado

El proyecto implementa un sistema web funcional para la gestión de solicitudes de soporte técnico.

La solución integra:

- Frontend desarrollado con React y TypeScript.
- API REST desarrollada con Spring Boot.
- Arquitectura por capas.
- Persistencia mediante MySQL.
- Spring Data JPA e Hibernate.
- Operaciones CRUD.
- Validaciones.
- Reglas de negocio.
- Manejo centralizado de excepciones.
- Documentación con Swagger.
- Pruebas funcionales.
- 8 pruebas automatizadas con JUnit.
- Integración entre las capas de vista, negocio y datos.