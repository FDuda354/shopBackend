--liquibase formatted sql
--changeset FDuda:6
insert into categories (id, name, description, slug) values(1, 'Inne', '', 'inne');
update products set category_id=1;
alter table products MODIFY category_id bigint not null;