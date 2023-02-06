--liquibase formatted sql
--changeset FDuda:6
INSERT INTO categories (id, name, description, slug) VALUES (1, 'Inne', '', 'inne');
UPDATE products SET category_id = 1;
ALTER TABLE products ALTER COLUMN category_id SET NOT NULL;
