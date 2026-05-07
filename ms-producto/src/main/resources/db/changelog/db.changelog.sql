--liquibase formatted sql

--changeset fabian:1
CREATE TABLE categoria (
    id_categoria BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(60)
);

--changeset fabian:2
CREATE TABLE producto (
    id_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_producto VARCHAR(60),
    precio DOUBLE,
    descripcion VARCHAR(255),
    categoria_id BIGINT,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id_categoria)
);

--changeset fabian:3
INSERT INTO categoria (nombre_categoria) VALUES
('Plato de fondo'),
('Ensalada'),
('bebestibles'),
('Sopas'),
('Acompañamiento');

--changeset fabian:4
INSERT INTO producto (nombre_producto, precio, descripcion, categoria_id) VALUES
('carbonada', 8500, 'Carbonada de vacuno', 1),
('sopaipillas', 15000, 'porcion de 6 sopaipillas, hechas con zapallo y harina', 5),
('pan', 3000, 'hecho al horno', 5),
('ensalada de lechuga', 2000, 'ensalada de lechuga hecha con lechugas de la zona', 2),
('coca-cola', 1000, 'coca-cola de 350ml', 3),
('jugo de naranja', 900, 'Jugo hecho con naranjas de la zona cultivadas por nusotros mismo', 3),
('pollo asando con arroz', 6000, 'pollo asado al horno, con una porcion de arroz', 1),
('humitas', 6000, 'Una porcion de tres humitas hechas de choclo y albahaca', 1),
('pescado con arroz', 6000, 'pescado de la zona acompañado de una porcion de arroz', 1),
('sopa de pollo', 3000, 'porcion de sopa de 450ml', 4);