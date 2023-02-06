--liquibase formatted sql
--changeset FDuda:10
CREATE TABLE shipments (
  id serial PRIMARY KEY,
  name varchar(64) NOT NULL,
  price numeric(6,2) NOT NULL,
  type varchar(32) NOT NULL,
  default_shipment boolean DEFAULT false
);
INSERT INTO shipments(name, price, type, default_shipment) VALUES ('Courier', 14.99, 'COURIER', true);
INSERT INTO shipments(name, price, type, default_shipment) VALUES ('Pick up', 0.0, 'PICKUP', false);
