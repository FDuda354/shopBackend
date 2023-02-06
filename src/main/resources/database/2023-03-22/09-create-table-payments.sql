--liquibase formatted sql
--changeset FDuda:12
CREATE TABLE payments (
  id serial PRIMARY KEY,
  name varchar(64) NOT NULL,
  type varchar(32) NOT NULL,
  default_payment boolean DEFAULT false,
  note text
);
INSERT INTO payments(id, name, type, default_payment, note)
VALUES (1, 'Bank Transfer', 'BANK_TRANSFER', true, 'Prosimy o dokonanie przelewu na konto:\n30 1030 1739 5825 1518 9904 4499\n w tytule proszę podać nr zamówienia');
