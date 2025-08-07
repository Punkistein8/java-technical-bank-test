CREATE SCHEMA IF NOT EXISTS cuenta;

CREATE SCHEMA IF NOT EXISTS movimientos;

CREATE TABLE IF NOT EXISTS cuenta.cuentas (
                                              num_cuenta VARCHAR(10) PRIMARY KEY,
    tipo_cuenta VARCHAR(255) NOT NULL,
    saldo_inicial DECIMAL(10, 2) NOT NULL,
    estado BOOLEAN NOT NULL,
    cliente_id BIGINT NOT NULL,
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES persona.clientes(cliente_id) ON DELETE RESTRICT
    );

CREATE TABLE IF NOT EXISTS movimientos.movimientos (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tipo_movimiento VARCHAR(255),
    valor DECIMAL(10, 2) NOT NULL,
    saldo DECIMAL(10, 2) NOT NULL,
    cuenta_num VARCHAR(10) NOT NULL,
    CONSTRAINT fk_cuenta FOREIGN KEY (cuenta_num) REFERENCES cuenta.cuentas(num_cuenta) ON DELETE RESTRICT
    );

CREATE INDEX IF NOT EXISTS idx_cuentas_cliente_id ON cuenta.cuentas(cliente_id);
CREATE INDEX IF NOT EXISTS idx_movimientos_cuenta_num ON movimientos.movimientos(cuenta_num);
CREATE INDEX IF NOT EXISTS idx_movimientos_fecha ON movimientos.movimientos(fecha);