--liquibase formatted sql
--changeset FDuda:7
create table reviews(
    id bigint not null auto_increment PRIMARY KEY,
    product_id bigint not null,
    author_name varchar(60) not null,
    content text
);