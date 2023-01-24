--liquibase formatted sql
--changeset FDuda:8
create table baskets(
    id bigint not null auto_increment PRIMARY KEY,
    created datetime not null
);
create table basket_items(
    id bigint not null auto_increment PRIMARY KEY,
    product_id bigint not null,
    quantity int,
    constraint fk_cart_item_product_id foreign key (product_id) references product(id)
);