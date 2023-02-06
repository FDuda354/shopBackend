--liquibase formatted sql
--changeset FDuda:5
ALTER TABLE products ADD COLUMN category_id bigint;
ALTER TABLE products DROP COLUMN category;
ALTER TABLE products ADD CONSTRAINT fk_products_category_id FOREIGN KEY (category_id) REFERENCES categories(id);
