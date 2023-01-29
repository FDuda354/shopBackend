--liquibase formatted sql
--changeset FDuda:13
alter table orders add column payment_id bigint;
update orders set payment_id = 1;
alter table orders MODIFY payment_id bigint NOT NULL;