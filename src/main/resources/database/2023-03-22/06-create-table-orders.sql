--liquibase formatted sql
--changeset FDuda:9
create table orders(
    id bigint not null auto_increment PRIMARY KEY,
    place_date datetime not null,
    order_status varchar(32) not null,
    gross_value decimal(15,2) not null,
    first_name varchar(30) not null,
    last_name varchar(30) not null,
    street varchar(60) not null,
    city varchar(30) not null,
    zip_code varchar(10) not null,
    email varchar(60) not null,
    phone varchar(20) not null
);
create table order_rows(
    id bigint not null auto_increment PRIMARY KEY,
    order_id bigint not null,
    product_id bigint not null,
    quantity int not null,
    price decimal(15,2) not null,
    constraint fk_order_rows_order_id foreign key (order_id) references orders(id),
    constraint fk_order_rows_product_id foreign key (product_id) references products(id)
);