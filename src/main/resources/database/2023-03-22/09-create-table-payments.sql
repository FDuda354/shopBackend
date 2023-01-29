--liquibase formatted sql
--changeset FDuda:12
create table payments(
    id bigint not null auto_increment PRIMARY KEY,
    name varchar(64) not null,
    type varchar(32) not null,
    default_payment boolean default false,
    note text
);
insert into payments(id, name, type, default_payment, note)
values (1, 'Bank Transfer', 'BANK_TRANSFER', true, 'Prosimy o dokonanie przelewu na konto:\n30 1030 1739 5825 1518 9904 4499\n w tytule proszę podać nr zamówienia');