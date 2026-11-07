# EFSRT2 - Sistema de Soporte Técnico

Proyecto académico desarrollado para el curso **Proyecto Integración de los Componentes de las Capas de Datos, Negocio y Vista**.

El sistema permite gestionar solicitudes de soporte técnico mediante una aplicación web basada en una arquitectura por capas, integrando frontend, backend, lógica de negocio, persistencia, autenticación, autorización por roles y seguimiento del ciclo completo de atención de incidencias.

## Integrantes

- Michael Jeymi Tacas Rocha
- Aldair Davis Hernandez Ramirez

## Organización de referencia

**GRZ & Asociados**

---

# Tecnologías

## Frontend

- React
- TypeScript
- Vite
- HTML
- CSS
- Fetch API
- Local Storage para gestión de sesión

## Backend

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Hibernate
- Jakarta Validation
- Spring Security
- JWT
- BCrypt
- OAuth2 Resource Server
- Swagger / OpenAPI
- Maven
- JUnit

## Base de datos

- MySQL
- MySQL Workbench

---

# Estructura del proyecto

```text
efsrt2-soporte-tecnico
├── database
│   └── setup.sql
│
├── efsrt2-soporte-tecnico-api
│   ├── src
│   ├── pom.xml
│   └── README.md
│
└── efsrt2-soporte-tecnico-web
    ├── src
    ├── package.json
    └── README.md
```

---

# Arquitectura

El sistema utiliza una arquitectura organizada por capas:

```text
Vista / Frontend
React + TypeScript
        ↓
Autenticación / JWT
        ↓
API / Controllers
Spring Boot
        ↓
Application Services
        ↓
Lógica de negocio
Services + Validators
        ↓
Acceso a datos
Repositories + JPA / Hibernate
        ↓
Base de datos
MySQL
```

Esta organización permite separar las responsabilidades de la interfaz, seguridad, lógica de negocio y persistencia.

---

# Roles del sistema

El sistema trabaja actualmente con tres roles:

```text
ADMINISTRADOR
TECNICO
CLIENTE
```

Cada rol tiene funciones y permisos diferentes.

---

## ADMINISTRADOR

El administrador puede:

- Iniciar sesión.
- Registrar solicitudes.
- Consultar todas las solicitudes.
- Seleccionar clientes.
- Asignar técnicos.
- Consultar técnicos disponibles.
- Registrar y administrar clientes.
- Activar o desactivar cuentas de clientes.
- Restablecer contraseñas de clientes.
- Consultar el detalle completo de los tickets.
- Cerrar solicitudes que se encuentren resueltas.
- Acceder a operaciones administrativas protegidas.

En el frontend dispone del formulario de registro de solicitudes y del listado general del sistema.

---

## TECNICO

El técnico puede:

- Iniciar sesión.
- Consultar únicamente las solicitudes que tiene asignadas.
- Consultar el detalle completo de cada ticket.
- Iniciar la atención de una solicitud.
- Cambiar una solicitud de `PENDIENTE` a `EN_PROCESO`.
- Marcar una solicitud como resuelta.
- Cambiar una solicitud de `EN_PROCESO` a `RESUELTA`.

El técnico no puede:

- Registrar solicitudes como administrador.
- Crear clientes.
- Administrar usuarios.
- Crear o eliminar técnicos.
- Cerrar una solicitud resuelta.
- Consultar solicitudes asignadas a otros técnicos mediante su endpoint específico.

---

## CLIENTE

El cliente puede:

- Crear una cuenta desde el registro público.
- Iniciar sesión.
- Registrar nuevas solicitudes de soporte.
- Consultar únicamente sus propias solicitudes.
- Consultar el detalle completo de sus tickets.
- Visualizar el técnico asignado.
- Consultar prioridad, gravedad, estado y fechas de seguimiento.

El cliente no puede:

- Seleccionar otro cliente.
- Elegir un técnico.
- Modificar directamente el estado.
- Cerrar solicitudes.
- Acceder a funciones administrativas.

El registro público siempre crea usuarios con:

```text
rol = CLIENTE
activo = true
```

El usuario no puede seleccionar libremente los roles `ADMINISTRADOR` o `TECNICO`.

---

# Registro público de clientes

Desde la pantalla de inicio de sesión se encuentra disponible la opción:

```text
¿No tienes una cuenta?
Crear cuenta
```

El formulario solicita:

```text
Nombre completo
Correo electrónico
Empresa
Contraseña
Confirmar contraseña
```

El frontend envía los datos a:

```http
POST /api/auth/registro
```

El backend crea:

```text
Cliente
   +
Usuario
   +
Rol CLIENTE
```

La contraseña es almacenada mediante BCrypt y nunca en texto plano.

---

# Funcionalidades principales

Actualmente el sistema permite:

- Registrar clientes.
- Registrar y administrar técnicos.
- Consultar técnicos disponibles.
- Registrar solicitudes de soporte.
- Registrar solicitudes desde una cuenta CLIENTE.
- Asignar prioridad.
- Asignar gravedad.
- Asignar técnicos.
- Consultar solicitudes.
- Consultar solicitudes por estado.
- Consultar solicitudes por prioridad.
- Consultar solicitudes propias del cliente.
- Consultar solicitudes asignadas al técnico.
- Cambiar el estado de las solicitudes.
- Validar disponibilidad del técnico.
- Validar transiciones entre estados.
- Calcular automáticamente la fecha límite.
- Registrar automáticamente las fechas del ciclo de atención.
- Consultar el detalle completo de un ticket.
- Iniciar sesión mediante correo y contraseña.
- Registrar nuevos clientes desde la web.
- Generar tokens JWT.
- Proteger endpoints mediante Spring Security.
- Diferenciar permisos según el rol.
- Mostrar interfaces diferentes para administrador, técnico y cliente.
- Cerrar sesión.
- Validar permisos para cambios de estado.

---

# Prioridad y gravedad

Las solicitudes manejan dos conceptos independientes.

## Prioridad

Representa la importancia u orden de atención:

```text
BAJA
MEDIA
ALTA
```

## Gravedad

Representa el impacto técnico de la incidencia:

```text
LEVE
MODERADA
GRAVE
CRITICA
```

La gravedad determina automáticamente el plazo máximo de atención.

| Gravedad | Tiempo máximo |
|---|---:|
| LEVE | 48 horas |
| MODERADA | 24 horas |
| GRAVE | 12 horas |
| CRITICA | 4 horas |

Ejemplo:

```text
Fecha de creación:
06/11/2026 05:10 p. m.

Gravedad:
GRAVE

Fecha límite:
07/11/2026 05:10 a. m.
```

La fecha límite no se ingresa manualmente desde el frontend.

---

# Ciclo de atención del ticket

Toda solicitud nueva inicia en:

```text
PENDIENTE
```

El ciclo completo es:

```text
PENDIENTE
    ↓
EN_PROCESO
    ↓
RESUELTA
    ↓
CERRADA
```

El flujo operativo por rol es:

```text
CLIENTE / ADMINISTRADOR
        ↓
Registra solicitud
        ↓
PENDIENTE

TÉCNICO
        ↓
Iniciar atención
        ↓
EN_PROCESO

TÉCNICO
        ↓
Marcar resuelta
        ↓
RESUELTA

ADMINISTRADOR
        ↓
Cerrar solicitud
        ↓
CERRADA
```

El sistema evita transiciones incorrectas como:

```text
PENDIENTE → CERRADA
```

---

# Fechas de seguimiento

Cada solicitud puede contener:

```text
fechaCreacion
fechaLimite
fechaInicioAtencion
fechaResolucion
fechaCierre
```

## Fecha de creación

Se genera automáticamente al registrar el ticket.

## Fecha límite

Se calcula automáticamente en función de la gravedad.

## Inicio de atención

Se registra cuando el técnico presiona:

```text
Iniciar atención
```

La solicitud cambia:

```text
PENDIENTE → EN_PROCESO
```

## Fecha de solución

Se registra cuando el técnico presiona:

```text
Marcar resuelta
```

La solicitud cambia:

```text
EN_PROCESO → RESUELTA
```

## Fecha de cierre

Se registra cuando el administrador cierra oficialmente la solicitud:

```text
RESUELTA → CERRADA
```

Estas fechas permiten determinar posteriormente indicadores como:

- Tiempo de respuesta.
- Tiempo de solución.
- Solicitudes resueltas dentro del plazo.
- Solicitudes resueltas fuera del plazo.

---

# Ver detalle del ticket

Las vistas utilizan tablas resumidas para evitar mostrar demasiada información simultáneamente.

Cada solicitud dispone de la opción:

```text
Ver detalle
```

El detalle muestra:

```text
Incidente
Descripción
Cliente
Técnico
Prioridad
Gravedad
Estado
Fecha de creación
Fecha límite
Inicio de atención
Fecha de solución
Fecha de cierre
```

El componente reutilizable utilizado es:

```text
SolicitudDetalleModal.tsx
```

---

# Vista del administrador

El administrador visualiza:

```text
Sistema de Soporte Técnico
Gestión y seguimiento de incidencias

Registrar nueva solicitud

Solicitudes registradas
```

La tabla principal muestra información resumida:

```text
ID
Incidente
Cliente
Técnico
Prioridad
Estado
Acciones
```

Acciones posibles:

```text
PENDIENTE
→ Ver detalle
→ Pendiente de atención

EN_PROCESO
→ Ver detalle
→ En atención

RESUELTA
→ Ver detalle
→ Cerrar solicitud

CERRADA
→ Ver detalle
→ Finalizada
```

---

# Vista del técnico

El técnico visualiza únicamente las solicitudes que tiene asignadas.

La tabla contiene:

```text
ID
Incidente
Cliente
Prioridad
Gravedad
Estado
Fecha límite
Acciones
```

Acciones:

```text
PENDIENTE
→ Ver detalle
→ Iniciar atención

EN_PROCESO
→ Ver detalle
→ Marcar resuelta

RESUELTA
→ Ver detalle
→ Pendiente de cierre

CERRADA
→ Ver detalle
→ Finalizada
```

El técnico no puede cerrar una solicitud.

---

# Vista del cliente

El cliente dispone de:

```text
Portal de Soporte

Nueva solicitud

Mis solicitudes
```

Al crear una solicitud completa únicamente:

```text
Incidente
Descripción
Prioridad
Gravedad
```

El backend obtiene automáticamente el cliente a partir del usuario autenticado.

No se solicita:

```text
clienteId
tecnicoId
estado
fechas
```

La tabla del cliente muestra:

```text
ID
Incidente
Prioridad
Estado
Técnico
Creación
Acción
```

Y mediante:

```text
Ver detalle
```

puede consultar toda la información del ticket.

---

# Seguridad

El sistema implementa seguridad mediante:

- Spring Security.
- JWT.
- BCrypt.
- Roles y permisos.
- Sesiones stateless.
- Bearer Token.
- Protección de endpoints.
- Validaciones de permisos en backend.

---

# Flujo de autenticación

```text
Usuario
   ↓
Login
   ↓
POST /api/auth/login
   ↓
Validación de credenciales
   ↓
BCrypt
   ↓
Generación JWT
   ↓
Frontend guarda el token
   ↓
Authorization: Bearer <JWT>
   ↓
Spring Security
   ↓
Recurso autorizado
```

El frontend almacena:

```text
auth_token
auth_user
```

en `Local Storage`.

El archivo:

```text
apiClient.ts
```

agrega automáticamente el JWT a las peticiones protegidas.

---

# Seguridad por rutas

La configuración de seguridad diferencia los módulos por rol:

```text
/api/admin/**
→ ADMINISTRADOR

/api/tecnico/**
→ TECNICO

/api/cliente/**
→ CLIENTE
```

Los siguientes endpoints son públicos:

```text
POST /api/auth/login
POST /api/auth/registro
```

---

# Validaciones de seguridad por rol

| Operación | Administrador | Técnico | Cliente |
|---|---:|---:|---:|
| Iniciar sesión | Sí | Sí | Sí |
| Registrarse públicamente | No aplica | No aplica | Sí |
| Consultar todas las solicitudes | Sí | No | No |
| Consultar solicitudes asignadas | Sí | Sí | No |
| Consultar solicitudes propias | Sí | No | Sí |
| Registrar solicitud | Sí | No | Sí |
| Seleccionar cliente | Sí | No | No |
| Asignar técnico | Sí | No | No |
| Iniciar atención | No | Sí | No |
| Marcar resuelta | No | Sí | No |
| Cerrar solicitud resuelta | Sí | No | No |
| Administrar clientes | Sí | No | No |

Cuando un usuario no se encuentra autenticado correctamente:

```text
401 Unauthorized
```

Cuando está autenticado pero no tiene permisos:

```text
403 Forbidden
```

---

# Validadores de negocio

El sistema utiliza validadores independientes para mantener una correcta separación de responsabilidades.

## TecnicoDisponibleValidator

Verifica que el técnico seleccionado se encuentre disponible antes de ser asignado.

## TransicionEstadoValidator

Verifica que la transición entre estados sea válida.

Ejemplo:

```text
PENDIENTE → EN_PROCESO ✅
PENDIENTE → CERRADA ❌
```

## PermisoCambioEstadoValidator

Comprueba si el usuario autenticado tiene permiso para realizar determinados cambios de estado.

```text
TÉCNICO
RESUELTA → CERRADA ❌

ADMINISTRADOR
RESUELTA → CERRADA ✅
```

---

# Servicios de fecha límite

La lógica para calcular el vencimiento de una solicitud está separada mediante:

```text
FechaLimiteService
FechaLimiteServiceImpl
```

Esta separación permite que la entidad `Solicitud` almacene la información mientras el servicio especializado aplica la regla de cálculo.

---

# Frontend

Entre los principales componentes y páginas se encuentran:

```text
src
├── components
│   ├── AppHeader.tsx
│   ├── SolicitudForm.tsx
│   ├── ClienteSolicitudForm.tsx
│   ├── SolicitudEstadoAction.tsx
│   └── SolicitudDetalleModal.tsx
│
├── pages
│   ├── LoginPage.tsx
│   ├── RegistroPage.tsx
│   ├── SolicitudesPage.tsx
│   ├── TecnicoSolicitudesPage.tsx
│   └── ClienteSolicitudesPage.tsx
│
├── services
│   ├── apiClient.ts
│   ├── authService.ts
│   ├── solicitudService.ts
│   ├── tecnicoSolicitudService.ts
│   ├── clienteSolicitudService.ts
│   ├── registroService.ts
│   ├── clienteService.ts
│   └── tecnicoService.ts
│
└── interfaces
```

---

# Servicios frontend por rol

## Administrador

```text
solicitudService.ts
```

Consume principalmente:

```text
/api/solicitudes
```

## Técnico

```text
tecnicoSolicitudService.ts
```

Consume:

```text
/api/tecnico/solicitudes
```

## Cliente

```text
clienteSolicitudService.ts
```

Consume:

```text
/api/cliente/solicitudes
```

## Registro público

```text
registroService.ts
```

Consume:

```text
/api/auth/registro
```

---

# API principal

## Autenticación

```http
POST /api/auth/login
POST /api/auth/registro
```

## Administración de clientes

```http
GET   /api/admin/clientes
POST  /api/admin/clientes
PUT   /api/admin/clientes/{id}
PATCH /api/admin/clientes/{id}/estado
PATCH /api/admin/clientes/{id}/password
```

## Solicitudes del administrador

```http
GET  /api/solicitudes
POST /api/solicitudes
PUT  /api/solicitudes/{id}
```

Consultas:

```http
GET /api/solicitudes/estado/{estado}
GET /api/solicitudes/prioridad/{prioridad}
```

## Solicitudes del técnico

```http
GET   /api/tecnico/solicitudes
PATCH /api/tecnico/solicitudes/{id}/iniciar
PATCH /api/tecnico/solicitudes/{id}/resolver
```

## Solicitudes del cliente

```http
GET  /api/cliente/solicitudes
POST /api/cliente/solicitudes
```

## Técnicos

```http
GET  /api/tecnicos
POST /api/tecnicos
GET  /api/tecnicos/disponibles
```

---

# Base de datos

La base de datos utilizada es:

```text
soporte_tecnico_db
```

Entre las principales tablas se encuentran:

```text
clientes
tecnicos
solicitudes
usuarios
```

La entidad `Usuario` permite relacionar la cuenta autenticada con un cliente o técnico.

Ejemplo conceptual:

```text
CLIENTE
cliente_id = 9
tecnico_id = null
rol = CLIENTE
```

```text
TECNICO
cliente_id = null
tecnico_id = 1
rol = TECNICO
```

```text
ADMINISTRADOR
cliente_id = null
tecnico_id = null
rol = ADMINISTRADOR
```

Las contraseñas se almacenan mediante BCrypt.

---

# 1. Preparar la base de datos

Abrir MySQL Workbench y ejecutar:

```text
database/setup.sql
```

---

# 2. Ejecutar el backend

Entrar a:

```powershell
cd efsrt2-soporte-tecnico-api
```

## Configurar MySQL

```powershell
$env:DB_PASSWORD="TU_CONTRASEÑA_MYSQL"
```

## Generar JWT_SECRET

```powershell
$bytes = New-Object byte[] 32
$rng = [System.Security.Cryptography.RandomNumberGenerator]::Create()
$rng.GetBytes($bytes)
$env:JWT_SECRET = [Convert]::ToBase64String($bytes)
$rng.Dispose()
```

## Configurar administrador inicial

```powershell
$env:ADMIN_EMAIL="admin@grzasociados.com"
$env:ADMIN_PASSWORD="TU_CONTRASEÑA_ADMIN"
```

Las contraseñas y secretos reales no deben almacenarse directamente en el repositorio.

## Ejecutar Spring Boot

```powershell
.\mvnw.cmd spring-boot:run
```

Backend:

```text
http://localhost:8080
```

Swagger:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI:

```text
http://localhost:8080/v3/api-docs
```

---

# 3. Ejecutar el frontend

Abrir otra terminal:

```powershell
cd efsrt2-soporte-tecnico-web
```

Instalar dependencias:

```powershell
npm install
```

Ejecutar:

```powershell
npm run dev
```

Normalmente:

```text
http://localhost:5173
```

Si el puerto está ocupado, Vite utilizará otro puerto disponible.

---

# Registro de una cuenta CLIENTE

Desde la web:

```text
Login
   ↓
Crear cuenta
   ↓
Nombre
Correo
Empresa
Contraseña
Confirmar contraseña
   ↓
Crear cuenta
```

También puede probarse mediante Swagger:

```http
POST /api/auth/registro
```

Ejemplo:

```json
{
  "nombre": "Pedro Quispe",
  "email": "pedro@empresa.com",
  "empresa": "Empresa ABC",
  "password": "Cliente123*"
}
```

La cuenta creada tendrá automáticamente:

```text
rol = CLIENTE
```

---

# Autenticación desde Swagger

Para probar endpoints protegidos:

```text
1. Abrir POST /api/auth/login.
2. Ingresar correo y contraseña.
3. Ejecutar.
4. Copiar el valor de "token".
5. Pulsar "Authorize".
6. Pegar el JWT.
7. Ejecutar los endpoints protegidos.
```

Swagger enviará:

```http
Authorization: Bearer <JWT>
```

---

# Pruebas automatizadas

Las pruebas del backend pueden ejecutarse con:

```powershell
.\mvnw.cmd clean test
```

Último resultado documentado:

```text
Tests run: 11
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

La suite incluye:

```text
SoporteTecnicoApiApplicationTests
→ 1 prueba

TecnicoDisponibleValidatorTest
→ 3 pruebas

TransicionEstadoValidatorTest
→ 4 pruebas

PermisoCambioEstadoValidatorTest
→ 3 pruebas
```

Total:

```text
11 pruebas automatizadas
```

---

# Pruebas funcionales

También se realizaron pruebas mediante:

- Swagger UI.
- Postman.
- Frontend React.
- Navegador web.
- MySQL Workbench.

Se comprobó el flujo:

```text
CLIENTE / ADMINISTRADOR
→ registra solicitud
→ PENDIENTE

TÉCNICO
→ Iniciar atención
→ EN_PROCESO

TÉCNICO
→ Marcar resuelta
→ RESUELTA

ADMINISTRADOR
→ Cerrar solicitud
→ CERRADA
```

También se verificaron:

- Login correcto.
- Registro público de clientes.
- Generación de JWT.
- Bearer Token.
- Acceso por roles.
- Restricción de endpoints administrativos.
- Solicitudes exclusivas por cliente.
- Solicitudes exclusivas por técnico.
- Cálculo automático de fecha límite.
- Registro automático de fechas.
- Cierre de sesión.
- Vistas diferenciadas por rol.
- Manejo de errores.
- Persistencia en MySQL.

---

# Principios SOLID

Durante el desarrollo se aplicaron principios SOLID y separación de responsabilidades.

## SRP - Responsabilidad única

Ejemplos:

```text
SolicitudForm
→ formulario del administrador

ClienteSolicitudForm
→ formulario del cliente

SolicitudDetalleModal
→ visualización del detalle

FechaLimiteService
→ cálculo de fecha límite

TecnicoSolicitudService
→ operaciones del técnico

ClienteSolicitudService
→ operaciones del cliente
```

## OCP - Abierto/Cerrado

Se pueden agregar nuevos servicios, validadores o métodos sin reescribir la lógica completa.

Ejemplo:

```text
apiClient
GET
POST
PUT
PATCH
```

## ISP - Segregación de interfaces

Los servicios se separan según responsabilidad y rol.

```text
SolicitudService
TecnicoSolicitudService
ClienteSolicitudService
RegistroClienteService
```

## DIP - Inversión de dependencias

Las clases reciben sus dependencias mediante constructor y Spring administra las implementaciones.

---

# Integración completa

El flujo general es:

```text
Usuario
   ↓
React + TypeScript
   ↓
Login / Registro / JWT
   ↓
API REST Spring Boot
   ↓
Spring Security
   ↓
Controllers
   ↓
Application Services / Services
   ↓
Validators
   ↓
Repositories
   ↓
JPA / Hibernate
   ↓
MySQL
```

El proyecto integra las tres capas principales solicitadas:

```text
Vista
→ React + TypeScript

Negocio
→ Spring Boot + Services + Validators

Datos
→ Repository + JPA + Hibernate + MySQL
```

---

# Repositorio

Repositorio general:

```text
https://github.com/MichaelTacas/efsrt2-soporte-tecnico
```

Incluye:

```text
database/
efsrt2-soporte-tecnico-api/
efsrt2-soporte-tecnico-web/
docs/
README.md
```

---

# Estado actual del proyecto

Actualmente se encuentra implementado:

- Frontend React + TypeScript.
- Backend Spring Boot.
- Base de datos MySQL.
- Arquitectura por capas.
- CRUD.
- DTO y Mapper.
- Application Services.
- Servicios.
- Repositorios.
- Validadores.
- Manejo global de errores.
- Swagger / OpenAPI.
- Spring Security.
- JWT.
- BCrypt.
- Roles `ADMINISTRADOR`, `TECNICO` y `CLIENTE`.
- Registro público de clientes.
- Control de acceso según rol.
- Login y cierre de sesión.
- Vistas diferenciadas según rol.
- Solicitudes propias del cliente.
- Solicitudes asignadas al técnico.
- Flujo completo de estados.
- Gravedad de incidencias.
- Fecha límite automática.
- Fecha de creación.
- Fecha de inicio de atención.
- Fecha de solución.
- Fecha de cierre.
- Ver detalle del ticket.
- Administración de clientes.
- Activación y desactivación de cuentas.
- Restablecimiento de contraseñas.
- Validación de permisos.
- 11 pruebas automatizadas documentadas.
- Pruebas funcionales de integración.

El sistema cuenta con una base funcional, modular y segura que puede continuar ampliándose mediante nuevos módulos, dashboards, reportes, métricas y funcionalidades de administración sin modificar innecesariamente los componentes existentes.