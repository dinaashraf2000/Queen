CREATE TABLE addresses
(
    id      BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    street  VARCHAR(255) NOT NULL,
    city    VARCHAR(255) NOT NULL,
    state   VARCHAR(255) NOT NULL,
    zip     VARCHAR(255) NOT NULL,
    user_id BIGINT       NOT NULL
);

CREATE TABLE categories
(
    id   TINYINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE products
(
    id            BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name          VARCHAR(255)   NOT NULL,
    price         DECIMAL(10, 2) NOT NULL,
    `description` LONGTEXT       NOT NULL,
    category_id   TINYINT NULL
);

CREATE TABLE profiles
(
    -- تم إزالة التعليقات العربية وإزالة UNSIGNED
    id             BIGINT NOT NULL PRIMARY KEY,
    bio            LONGTEXT NULL,
    phone_number   VARCHAR(15) NULL,
    date_of_birth  date NULL,
    loyalty_points INT DEFAULT 0 NULL
);

CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE wishlist
(
    product_id BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    PRIMARY KEY (product_id, user_id)
);

-- المفاتيح الأجنبية والفهارس

ALTER TABLE addresses
    ADD CONSTRAINT addresses_users_id_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION;

CREATE INDEX addresses_users_id_fk ON addresses (user_id);

ALTER TABLE products
    ADD CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE NO ACTION;

CREATE INDEX fk_category ON products (category_id);

ALTER TABLE wishlist
    ADD CONSTRAINT fk_wishlist_on_product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE;

ALTER TABLE wishlist
    ADD CONSTRAINT fk_wishlist_on_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION;

CREATE INDEX fk_wishlist_on_user ON wishlist (user_id);

ALTER TABLE profiles
    ADD CONSTRAINT profiles_ibfk_1 FOREIGN KEY (id) REFERENCES users (id) ON DELETE NO ACTION;