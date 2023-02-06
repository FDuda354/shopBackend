--liquibase formatted sql
--changeset FDuda:3
ALTER TABLE products ADD CONSTRAINT uq_product_slug UNIQUE (slug);