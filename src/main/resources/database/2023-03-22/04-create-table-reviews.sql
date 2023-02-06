--liquibase formatted sql
--changeset FDuda:7
CREATE TABLE reviews (
    id serial PRIMARY KEY,
    product_id bigint NOT NULL,
    author_name varchar(60) NOT NULL,
    content text
);