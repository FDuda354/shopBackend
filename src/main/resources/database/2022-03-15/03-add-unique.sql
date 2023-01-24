--liquibase formatted sql
--changeset FDuda:3
alter table products add constraint uq_product_slug unique key (slug);