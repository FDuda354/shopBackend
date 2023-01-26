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
    basket_id bigint not null,
    constraint fk_basket_items_product_id foreign key (product_id) references products(id),
    constraint fk_basket_items_basket_id foreign key (basket_id) references baskets(id)
);