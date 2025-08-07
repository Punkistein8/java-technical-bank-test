-- Insertar datos de ejemplo en 'clientes'
INSERT INTO persona.clientes (nombres, genero, edad, identificacion, direccion, telefono, contrasenia, estado)
VALUES ('Juan Pérez', 'MASCULINO', 30, '1717171712', 'Calle Falsa 123, Quito', '0987654321', 'securePass123', true),
       ('María López', 'FEMENINO', 25, '1701234567', 'Av. Siempre Viva 456, Guayaquil', '0998765432', 'password456',
        true),
       ('Carlos Ruiz', 'MASCULINO', 40, '1723456784', 'Calle Principal 789, Cuenca', '0976543210', 'pass789',
        false) ON CONFLICT (cliente_id) DO NOTHING;