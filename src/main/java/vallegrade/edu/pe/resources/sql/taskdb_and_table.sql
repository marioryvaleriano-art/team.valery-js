CREATE DATABASE bd_tienda;

USE bd_tienda;

CREATE TABLE producto (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          precio DECIMAL(10 , 2 ) NOT NULL,
                          stock INT NOT NULL
);

-- Datos de ejemplo
INSERT INTO producto (nombre, precio, stock) VALUES
                                                 ('Laptop Lenovo', 2500.00, 10),
                                                 ('Mouse Inalámbrico', 60.00, 50),
                                                 ('Teclado Mecánico', 180.00, 20);

Select * from producto;