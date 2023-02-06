--liquibase formatted sql
--changeset FDuda:13
ALTER TABLE orders ADD COLUMN payment_id bigint;
UPDATE orders SET payment_id = 1;
ALTER TABLE orders ALTER COLUMN payment_id SET NOT NULL;