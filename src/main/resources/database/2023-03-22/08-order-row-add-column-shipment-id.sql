--liquibase formatted sql
--changeset FDuda:11
alter table order_rows MODIFY product_id bigint;
alter table order_rows add shipment_id bigint;
alter table order_rows add constraint fk_order_rows_shipment_id foreign key (shipment_id) references shipments(id);