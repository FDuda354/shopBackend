--liquibase formatted sql
--changeset FDuda:16
alter table orders add user_id bigint;
--changeset FDuda:17
alter table orders add constraint fk_orders_user_id foreign key (user_id) references users(id);