--liquibase formatted sql
--changeset FDuda:5
alter table products add category_id bigint after category;
alter table products drop column category;
alter table products add constraint fk_products_category_id foreign key (category_id) references categories(id);