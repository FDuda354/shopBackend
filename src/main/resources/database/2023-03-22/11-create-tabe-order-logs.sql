--liquibase formatted sql
--changeset FDuda:14
CREATE TABLE order_logs (
id serial PRIMARY KEY,
order_id bigint NOT NULL,
created timestamptz NOT NULL,
note text
);