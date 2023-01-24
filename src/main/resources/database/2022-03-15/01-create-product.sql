--liquibase formatted sql
--changeset FDuda:1
create table products (
    id bigint not null auto_increment PRIMARY KEY,
    name varchar(255) not null,
    category varchar(255) not null,
    description varchar(100) not null,
    full_description text,
    price decimal(9,2) not null,
    currency varchar(3) not null,
    image varchar(255),
    slug varchar(255)
);
