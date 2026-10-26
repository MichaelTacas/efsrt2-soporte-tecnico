CREATE DATABASE IF NOT EXISTS soporte_tecnico_db;

USE soporte_tecnico_db;


-- =========================================
-- TABLA CLIENTES
-- =========================================

CREATE TABLE IF NOT EXISTS clientes (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    empresa VARCHAR(150)
    );


-- =========================================
-- TABLA TECNICOS
-- =========================================

CREATE TABLE IF NOT EXISTS tecnicos (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        nombre VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    disponible BOOLEAN NOT NULL DEFAULT TRUE
    );


-- =========================================
-- TABLA SOLICITUDES
-- =========================================

CREATE TABLE IF NOT EXISTS solicitudes (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           titulo VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    prioridad VARCHAR(20) NOT NULL,
    estado VARCHAR(30) NOT NULL,

    cliente_id BIGINT NOT NULL,
    tecnico_id BIGINT NULL,

    CONSTRAINT fk_solicitud_cliente
    FOREIGN KEY (cliente_id)
    REFERENCES clientes(id),

    CONSTRAINT fk_solicitud_tecnico
    FOREIGN KEY (tecnico_id)
    REFERENCES tecnicos(id)
    );


-- =========================================
-- DATOS DE EJEMPLO
-- =========================================

INSERT INTO clientes (nombre, email, empresa)
SELECT 'Juan Cruz', 'juan.cruz@grzasociados.com', 'GRZ & Asociados'
    WHERE NOT EXISTS (
    SELECT 1
    FROM clientes
    WHERE email = 'juan.cruz@grzasociados.com'
);


INSERT INTO clientes (nombre, email, empresa)
SELECT 'Ana Torres', 'ana.torres@grzasociados.com', 'GRZ & Asociados'
    WHERE NOT EXISTS (
    SELECT 1
    FROM clientes
    WHERE email = 'ana.torres@grzasociados.com'
);


INSERT INTO tecnicos (nombre, especialidad, disponible)
SELECT 'Carlos Mendoza', 'Soporte de Hardware', TRUE
    WHERE NOT EXISTS (
    SELECT 1
    FROM tecnicos
    WHERE nombre = 'Carlos Mendoza'
);


INSERT INTO tecnicos (nombre, especialidad, disponible)
SELECT 'Luis Torres', 'Soporte de Software', TRUE
    WHERE NOT EXISTS (
    SELECT 1
    FROM tecnicos
    WHERE nombre = 'Luis Torres'
);