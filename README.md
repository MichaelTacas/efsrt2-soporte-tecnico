# EFSRT2 - Sistema de Soporte Técnico

Proyecto académico desarrollado para el curso **Proyecto Integración de los Componentes de las Capas de Datos, Negocio y Vista**.

El sistema permite gestionar solicitudes de soporte técnico mediante una aplicación web basada en una arquitectura por capas.

## Integrantes

- Michael Jeymi Tacas Rocha
- Aldair Davis Hernandez Ramirez
- Rosmery Espinoza Quito

## Organización de referencia

**GRZ & Asociados**

## Tecnologías

### Frontend
- React
- TypeScript
- Vite
- HTML
- CSS
- Fetch API

### Backend
- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Hibernate
- Jakarta Validation
- Swagger / OpenAPI
- Maven

### Base de datos
- MySQL

## Estructura del proyecto

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

## Arquitectura

El sistema utiliza una arquitectura organizada en capas:

```text
Vista / Frontend
React + TypeScript
        ↓
API / Controller
Spring Boot
        ↓
Lógica de negocio
Application Service + Service + Validators
        ↓
Acceso a datos
Repository + JPA / Hibernate
        ↓
Base de datos
MySQL
```

## Funcionalidades principales

- Registrar clientes.
- Registrar y administrar técnicos.
- Consultar técnicos disponibles.
- Registrar solicitudes de soporte.
- Asignar prioridades.
- Asignar técnicos.
- Listar solicitudes.
- Consultar solicitudes por estado.
- Consultar solicitudes por prioridad.
- Cambiar el estado de las solicitudes.
- Validar disponibilidad del técnico.
- Validar las transiciones entre estados.

Los estados siguen el flujo:

```text
PENDIENTE
    ↓
EN_PROCESO
    ↓
RESUELTA
    ↓
CERRADA
```

## 1. Preparar la base de datos

Abrir MySQL Workbench y ejecutar:

```text
database/setup.sql
```

La base de datos utilizada es:

```text
soporte_tecnico_db
```

## 2. Ejecutar el backend

Entrar a:

```powershell
cd efsrt2-soporte-tecnico-api
```

Configurar la contraseña de MySQL:

```powershell
$env:DB_PASSWORD="TU_CONTRASEÑA"
```

Ejecutar Spring Boot:

```powershell
.\mvnw.cmd spring-boot:run
```

El backend estará disponible en:

```text
http://localhost:8080
```

Swagger:

```text
http://localhost:8080/swagger-ui/index.html
```

## 3. Ejecutar el frontend

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

Abrir:

```text
http://localhost:5173
```

## Pruebas

Las pruebas del backend pueden ejecutarse con:

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

La suite incluye 1 prueba de carga del contexto de Spring Boot, 3 pruebas de `TecnicoDisponibleValidator` y 4 pruebas de `TransicionEstadoValidator`. Como mejora futura se ampliará la cobertura hacia servicios (JUnit/Mockito) y pruebas de integración.

## Seguridad

Las credenciales de MySQL no se almacenan directamente en el repositorio.

La contraseña se configura mediante:

```text
DB_PASSWORD
```

Como mejora futura se propone incorporar:

- Spring Security.
- JWT.
- Roles y permisos.
- BCrypt.

## Principios de diseño

Durante el desarrollo se aplicaron separación de responsabilidades y principios SOLID.

Se utilizaron componentes independientes como:

- Controllers.
- Application Services.
- Services.
- Validators.
- Repositories.
- DTO.
- Mapper.
- Componentes React.
- Servicios frontend.

Esto permite reducir el acoplamiento y facilita el mantenimiento del sistema.