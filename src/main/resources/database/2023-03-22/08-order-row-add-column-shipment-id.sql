--liquibase formatted sql
--changeset FDuda:11
ALTER TABLE order_rows ALTER COLUMN product_id TYPE bigint;
ALTER TABLE order_rows ADD COLUMN shipment_id bigint;
ALTER TABLE order_rows ADD CONSTRAINT fk_order_rows_shipment_id FOREIGN KEY (shipment_id) REFERENCES shipments(id);
