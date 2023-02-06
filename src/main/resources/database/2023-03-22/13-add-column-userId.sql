--liquibase formatted sql
--changeset FDuda:16
ALTER TABLE orders ADD COLUMN user_id bigint;

--changeset FDuda:17
ALTER TABLE orders ADD CONSTRAINT fk_orders_user_id FOREIGN KEY (user_id) REFERENCES users (id);