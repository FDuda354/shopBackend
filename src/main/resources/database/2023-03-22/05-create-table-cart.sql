--liquibase formatted sql
--changeset FDuda:8
CREATE TABLE baskets (
  id serial PRIMARY KEY,
  created timestamp NOT NULL
);
CREATE TABLE basket_items (
  id serial PRIMARY KEY,
  product_id bigint NOT NULL,
  quantity int,
  basket_id bigint NOT NULL,
  CONSTRAINT fk_basket_items_product_id FOREIGN KEY (product_id) REFERENCES products(id),
  CONSTRAINT fk_basket_items_basket_id FOREIGN KEY (basket_id) REFERENCES baskets(id)
);