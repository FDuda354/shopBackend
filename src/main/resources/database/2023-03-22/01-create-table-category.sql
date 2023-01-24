--liquibase formatted sql
--changeset FDuda:4
create table categories(
    id bigint not null auto_increment PRIMARY KEY,
    name varchar(255) not null,
    description text,
    slug varchar(255) not null
);