# Sistema de Soporte Técnico - Frontend

Frontend web desarrollado para la gestión de solicitudes de soporte técnico.

La aplicación permite registrar solicitudes, seleccionar clientes y técnicos disponibles, consultar las solicitudes registradas y actualizar su estado mediante una interfaz web conectada con la API REST desarrollada en Spring Boot.

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
- Ingresar título y descripción del problema.
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
- Cambiar el estado de las solicitudes.
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

Las reglas definitivas para permitir o rechazar una transición se validan en el backend.

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

- Cargar las solicitudes.
- Mostrar el formulario.
- Mostrar el listado de solicitudes.
- Coordinar el registro de nuevas solicitudes.
- Coordinar el cambio de estado.

### SolicitudForm

Componente responsable de capturar los datos para registrar una nueva solicitud.

Permite seleccionar:

- Título.
- Descripción.
- Prioridad.
- Cliente.
- Técnico disponible.

### SolicitudEstadoAction

Componente responsable de mostrar la acción disponible según el estado actual de una solicitud.

Ejemplo:

```text
EN_PROCESO → Marcar resuelta
RESUELTA → Cerrar
```

## Servicios

La comunicación con el backend se encuentra separada de los componentes visuales.

### apiClient.ts

Centraliza las operaciones HTTP utilizadas por la aplicación.

Actualmente soporta:

```text
GET
POST
PUT
```

### solicitudService.ts

Gestiona las operaciones relacionadas con solicitudes.

### clienteService.ts

Obtiene los clientes registrados desde el backend.

### tecnicoService.ts

Obtiene los técnicos disponibles desde el backend.

Esta separación permite mantener un menor acoplamiento entre la interfaz y la comunicación con la API.

## Integración con el backend

El frontend consume una API REST desarrollada con Spring Boot.

El flujo general es:

```text
Usuario
   ↓
React
   ↓
Services
   ↓
apiClient
   ↓
API REST Spring Boot
   ↓
Lógica de negocio
   ↓
Repository
   ↓
MySQL
```

El backend se ejecuta en:

```text
http://localhost:8080
```

El frontend se ejecuta por defecto en:

```text
http://localhost:5173
```

## Proxy de desarrollo

Vite está configurado para redirigir las solicitudes realizadas a:

```text
/api
```

hacia el backend:

```text
http://localhost:8080
```

Por ejemplo:

```text
Frontend:
GET /api/solicitudes

Vite Proxy:
↓

Backend:
http://localhost:8080/api/solicitudes
```

Esto evita colocar directamente la URL completa del backend dentro de cada servicio.

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

Para utilizar correctamente el frontend se deben tener activos los tres componentes:

```text
MySQL
  ↓
Spring Boot :8080
  ↓
React / Vite :5173
```

1. Ejecutar el script de base de datos ubicado en:

```text
database/setup.sql
```

2. Iniciar el backend Spring Boot.

3. Iniciar el frontend:

```powershell
npm run dev
```

4. Abrir:

```text
http://localhost:5173/
```

## Diseño de la interfaz

La interfaz utiliza un diseño oscuro y responsive.

Se priorizó:

- Organización visual clara.
- Separación entre formulario y listado.
- Campos correctamente identificados.
- Uso de listas desplegables para clientes y técnicos.
- Visualización diferenciada de prioridad y estado.
- Adaptación a diferentes tamaños de pantalla.

## Principios aplicados

Durante el desarrollo se buscó mantener responsabilidades separadas.

Ejemplo:

```text
SolicitudForm
→ captura información

SolicitudEstadoAction
→ controla la acción visual de estado

SolicitudesPage
→ coordina la pantalla

Services
→ manejan operaciones del dominio

apiClient
→ maneja comunicación HTTP
```

Esto facilita el mantenimiento del frontend y evita colocar toda la lógica en un único componente.

## Backend relacionado

Este frontend forma parte del proyecto:

```text
efsrt2-soporte-tecnico
```

y trabaja en conjunto con:

```text
efsrt2-soporte-tecnico-api
```

La persistencia de los datos se realiza mediante MySQL desde el backend.