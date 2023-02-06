--liquibase formatted sql
--changeset FDuda:1
CREATE TABLE products (
    id serial PRIMARY KEY,
    name varchar(255) NOT NULL,
    category varchar(255) NOT NULL,
    description varchar(100) NOT NULL,
    full_description text,
    price numeric(9,2) NOT NULL,
    currency varchar(3) NOT NULL,
    image varchar(255),
    slug varchar(255)
);
