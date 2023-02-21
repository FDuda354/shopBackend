--liquibase formatted sql
--changeset FDuda:5
INSERT INTO payments(id, name, type, default_payment, note)
VALUES
(1, 'Bank Transfer', 'BANK_TRANSFER', true, 'Prosimy o dokonanie przelewu na konto:\n00 0000 0000 0000 0000 0000 0000\n w tytule proszę podać nr zamówienia'),
(2, 'Przelewy 24', 'P24_ONLINE', false, '');


