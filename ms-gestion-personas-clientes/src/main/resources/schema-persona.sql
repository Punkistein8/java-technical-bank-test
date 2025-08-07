-- Crear esquema 'persona'
CREATE SCHEMA IF NOT EXISTS persona;

-- Crear tabla 'clientes' en el esquema 'persona'
CREATE TABLE IF NOT EXISTS persona.clientes (
    cliente_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombres VARCHAR(255),
    genero VARCHAR(255),
    edad INTEGER,
    identificacion VARCHAR(255) NOT NULL UNIQUE,
    direccion VARCHAR(255),
    telefono VARCHAR(255),
    contrasenia VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE
    );

-- Crear índice para mejorar el rendimiento
CREATE INDEX IF NOT EXISTS idx_clientes_identificacion ON persona.clientes(identificacion);