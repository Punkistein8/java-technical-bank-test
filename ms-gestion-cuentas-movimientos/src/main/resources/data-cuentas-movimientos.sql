-- Insertar datos de ejemplo en 'cuentas'
INSERT INTO cuenta.cuentas (num_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id)
VALUES ('1234567890', 'AHORRO', 500.00, true, 1),
       ('0987654321', 'CORRIENTE', 1000.00, true, 2),
       ('1122334455', 'AHORRO', 200.00, false, 3) ON CONFLICT (num_cuenta) DO NOTHING;

-- Insertar datos de ejemplo en 'movimientos'
INSERT INTO movimientos.movimientos (fecha, tipo_movimiento, valor, saldo, cuenta_num)
VALUES ('2025-01-15 10:00:00', 'Deposito de 200.00', 200.00, 700.00, '1234567890'),
       ('2025-02-01 14:30:00', 'Retiro de 100.00', -100.00, 600.00, '1234567890'),
       ('2025-01-20 09:15:00', 'Deposito de 300.00', 300.00, 1300.00, '0987654321'),
       ('2025-02-10 16:45:00', 'Retiro de 50.00', -50.00, 1250.00, '0987654321'),
       ('2025-01-25 12:00:00', 'Deposito de 150.00', 150.00, 350.00, '1122334455') ON CONFLICT (id) DO NOTHING;