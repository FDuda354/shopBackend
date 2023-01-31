--liquibase formatted sql
--changeset FDuda:14
create table order_logs(
    id bigint not null auto_increment PRIMARY KEY,
    order_id bigint not null,
    created datetime not null,
    note text
);