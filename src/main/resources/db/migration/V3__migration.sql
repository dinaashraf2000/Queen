create table cart_item
(
    id         bigint auto_increment
        primary key,
    cart_id    binary(16)    not null,
    product_id bigint           not null,
    quantity   int default 1 not null,
    constraint cart_item_cart_product_uniqe
        unique (cart_id, product_id),
    constraint cart_item_cart_id_fk
        foreign key (cart_id) references cart (id)
            on delete cascade,
    constraint cart_item_products_id_fk
        foreign key (product_id) references products (id)
            on delete cascade
);

