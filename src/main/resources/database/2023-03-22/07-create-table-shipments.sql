--liquibase formatted sql
--changeset FDuda:10
create table shipments(
    id bigint not null auto_increment PRIMARY KEY,
    name varchar(64) not null,
    price decimal(6,2) not null,
    type varchar(32) not null,
    default_shipment boolean default false
);
insert into shipments(name, price, type, default_shipment) values ('Kurier', 14.99, 'COURIER', true);
insert into shipments(name, price, type, default_shipment) values ('Odbiór osobisty', 0.0, 'PICKUP', false);