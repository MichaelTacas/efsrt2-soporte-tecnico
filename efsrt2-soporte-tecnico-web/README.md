# Sistema de Soporte Técnico - Frontend

Frontend web desarrollado para la gestión de solicitudes de soporte técnico.

La aplicación permite registrar solicitudes, seleccionar clientes y técnicos disponibles, consultar las solicitudes registradas y actualizar su estado mediante una interfaz web conectada con una API REST desarrollada en Spring Boot.

## Tecnologías utilizadas

- React
- TypeScript
- Vite
- HTML
- CSS
- Fetch API

## Funcionalidades implementadas

Actualmente el frontend permite:

- Registrar nuevas solicitudes de soporte.
- Ingresar el título y descripción del problema.
- Seleccionar la prioridad de la solicitud:
  - BAJA
  - MEDIA
  - ALTA
- Seleccionar un cliente registrado.
- Consultar y seleccionar técnicos disponibles.
- Registrar solicitudes sin técnico asignado.
- Listar las solicitudes existentes.
- Mostrar información del cliente y técnico asociado.
- Visualizar la prioridad y estado de cada solicitud.
- Actualizar el estado de las solicitudes.
- Mostrar mensajes de éxito o error durante las operaciones.

## Estados de las solicitudes

Las solicitudes siguen el siguiente flujo:

```text
PENDIENTE
    ↓
EN_PROCESO
    ↓
RESUELTA
    ↓
CERRADA
```

Desde la interfaz se muestran las acciones correspondientes según el estado actual:

```text
PENDIENTE
→ Iniciar atención

EN_PROCESO
→ Marcar resuelta

RESUELTA
→ Cerrar

CERRADA
→ Finalizada
```

Las reglas de negocio que permiten o rechazan las transiciones de estado se validan en el backend.

## Estructura principal

```text
src
├── components
│   ├── SolicitudForm.tsx
│   └── SolicitudEstadoAction.tsx
│
├── interfaces
│   ├── Cliente.ts
│   ├── Solicitud.ts
│   ├── SolicitudRequest.ts
│   └── Tecnico.ts
│
├── pages
│   └── SolicitudesPage.tsx
│
├── services
│   ├── apiClient.ts
│   ├── clienteService.ts
│   ├── solicitudService.ts
│   └── tecnicoService.ts
│
├── styles
│   └── Solicitudes.css
│
├── App.tsx
├── index.css
└── main.tsx
```

## Responsabilidad de los componentes

### SolicitudesPage

Es la pantalla principal del sistema.

Se encarga de:

- Cargar las solicitudes registradas.
- Mostrar el formulario de registro.
- Mostrar el listado de solicitudes.
- Coordinar el registro de nuevas solicitudes.
- Coordinar la actualización del estado.

### SolicitudForm

Componente responsable de capturar los datos necesarios para registrar una nueva solicitud.

Permite ingresar o seleccionar:

- Título.
- Descripción.
- Prioridad.
- Cliente.
- Técnico disponible.

### SolicitudEstadoAction

Componente responsable de mostrar la acción disponible de acuerdo con el estado actual de una solicitud.

Ejemplo:

```text
PENDIENTE → Iniciar atención
EN_PROCESO → Marcar resuelta
RESUELTA → Cerrar
CERRADA → Finalizada
```

## Servicios

La comunicación con el backend se encuentra separada de los componentes visuales.

### apiClient.ts

Centraliza las operaciones HTTP utilizadas por la aplicación.

Actualmente permite trabajar con operaciones como:

```text
GET
POST
PUT
```

### solicitudService.ts

Gestiona las operaciones relacionadas con las solicitudes de soporte.

### clienteService.ts

Obtiene desde el backend la información de los clientes registrados.

### tecnicoService.ts

Obtiene desde el backend los técnicos disponibles para ser asignados a una solicitud.

Esta separación ayuda a reducir el acoplamiento entre la interfaz y la comunicación con la API.

## Integración con el backend

El frontend consume una API REST desarrollada con Spring Boot.

El flujo general del sistema es:

```text
Usuario
   ↓
React + TypeScript
   ↓
Services
   ↓
apiClient
   ↓
API REST Spring Boot
   ↓
Lógica de negocio
   ↓
Repository / JPA
   ↓
MySQL
```

El backend se ejecuta normalmente en:

```text
http://localhost:8080
```

El frontend se ejecuta normalmente en:

```text
http://localhost:5173
```

Si el puerto `5173` se encuentra ocupado, Vite puede utilizar otro puerto disponible.

## Proxy de desarrollo

Vite está configurado para redirigir las solicitudes realizadas a:

```text
/api
```

hacia el backend:

```text
http://localhost:8080
```

Ejemplo:

```text
Frontend
GET /api/solicitudes

        ↓

Vite Proxy

        ↓

Backend
http://localhost:8080/api/solicitudes
```

Esto permite centralizar la comunicación con el backend y evita colocar la URL completa de la API en cada componente.

## Requisitos

Antes de ejecutar el frontend se necesita:

- Node.js instalado.
- npm instalado.
- Backend Spring Boot en ejecución.
- Base de datos MySQL configurada.

## Instalación

Desde la carpeta del frontend:

```powershell
cd efsrt2-soporte-tecnico-web
```

Instalar las dependencias:

```powershell
npm install
```

## Ejecución

Iniciar el servidor de desarrollo:

```powershell
npm run dev
```

Vite mostrará una dirección similar a:

```text
http://localhost:5173/
```

Abrir esa dirección en el navegador.

## Ejecución completa del sistema

Para utilizar correctamente la aplicación se deben tener activos los tres componentes principales:

```text
MySQL
  ↓
Spring Boot :8080
  ↓
React / Vite :5173
```

### 1. Preparar la base de datos

Desde la carpeta raíz del proyecto se encuentra el script:

```text
database/setup.sql
```

Este script permite preparar la base de datos utilizada por el sistema.

### 2. Iniciar el backend

Ejecutar la aplicación Spring Boot ubicada en:

```text
efsrt2-soporte-tecnico-api
```

El backend deberá estar disponible en:

```text
http://localhost:8080
```

### 3. Iniciar el frontend

Desde:

```text
efsrt2-soporte-tecnico-web
```

ejecutar:

```powershell
npm run dev
```

### 4. Abrir la aplicación

Ingresar desde el navegador a la dirección mostrada por Vite, normalmente:

```text
http://localhost:5173/
```

## Diseño de la interfaz

La interfaz utiliza un diseño oscuro y adaptable a diferentes tamaños de pantalla.

Se priorizó:

- Organización visual clara.
- Separación entre formulario y listado.
- Campos correctamente identificados.
- Uso de listas desplegables para clientes y técnicos.
- Visualización clara de la prioridad y estado.
- Acciones específicas según el estado de cada solicitud.
- Adaptación de la interfaz a diferentes resoluciones.

## Principios aplicados

Durante el desarrollo del frontend se buscó mantener responsabilidades separadas entre los diferentes componentes.

```text
SolicitudForm
→ captura y envía los datos del formulario

SolicitudEstadoAction
→ muestra las acciones relacionadas con el estado

SolicitudesPage
→ coordina la pantalla principal

Services
→ encapsulan las operaciones relacionadas con la API

apiClient
→ centraliza la comunicación HTTP
```

Esta organización facilita el mantenimiento del código y evita concentrar toda la lógica de la aplicación en un único componente.

También permite realizar cambios en una parte del sistema sin afectar innecesariamente a las demás.

## Relación con el proyecto completo

Este frontend forma parte del proyecto:

```text
efsrt2-soporte-tecnico
```

y trabaja en conjunto con el backend:

```text
efsrt2-soporte-tecnico-api
```

La persistencia de la información se realiza en MySQL a través del backend.

La arquitectura general utilizada es:

```text
Vista
React + TypeScript
        ↓
API / Controller
Spring Boot
        ↓
Lógica de negocio
Services + Validators
        ↓
Acceso a datos
Repository + JPA / Hibernate
        ↓
Base de datos
MySQL
```