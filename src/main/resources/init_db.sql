CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8;

CREATE TABLE `internet_shop`.`products`
(
    `product_id` BIGINT(11)   NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(225) NOT NULL,
    `price`      BIGINT(11)   NOT NULL,
    `deleted`    TINYINT(11)  NULL DEFAULT FALSE,
    PRIMARY KEY (`product_id`)
);

CREATE TABLE internet_shop.`users`
(
    `user_id`  BIGINT(11)   NOT NULL AUTO_INCREMENT,
    `login`    VARCHAR(256) NOT NULL,
    `password` VARCHAR(256) NOT NULL,
    `deleted`  TINYINT      NULL DEFAULT 0,
    PRIMARY KEY (`user_id`)
);

CREATE TABLE `internet_shop`.`roles`
(
    `role_id`   BIGINT(11)   NOT NULL AUTO_INCREMENT,
    `role_name` VARCHAR(256) NOT NULL,
    PRIMARY KEY (`role_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

INSERT INTO `internet_shop`.`roles` (`role_id`, `role_name`) VALUES ('1', 'USER');
INSERT INTO `internet_shop`.`roles` (`role_id`, `role_name`) VALUES ('2', 'ADMIN');

CREATE TABLE `internet_shop`.`users_roles`
(
    `id`      BIGINT(11) NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT(11) NOT NULL,
    `role_id` BIGINT(11) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `user_idx` (`user_id` ASC) VISIBLE,
    INDEX `role_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `user`
        FOREIGN KEY (`user_id`)
            REFERENCES `internet_shop`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `role`
        FOREIGN KEY (`role_id`)
            REFERENCES `internet_shop`.`roles` (`role_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE `internet_shop`.`orders`
(
    `order_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `user_id`  BIGINT(11) NOT NULL,
    `deleted`  TINYINT    NULL DEFAULT 0,
    PRIMARY KEY (`order_id`),
    INDEX `orders_users_fk_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `orders_users_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `internet_shop`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `internet_shop`.`orders_products`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `order_id`   BIGINT NOT NULL,
    `product_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `order_idx` (`order_id` ASC) VISIBLE,
    INDEX `product_idx` (`product_id` ASC) VISIBLE,
    CONSTRAINT `order`
        FOREIGN KEY (`order_id`)
            REFERENCES `internet_shop`.`orders` (`order_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `product`
        FOREIGN KEY (`product_id`)
            REFERENCES `internet_shop`.`products` (`product_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE `internet_shop`.`shopping_carts`
(
    `cart_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT(11) NOT NULL,
    `deleted` TINYINT    NULL DEFAULT 0,
    PRIMARY KEY (`cart_id`),
    INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `user_id`
        FOREIGN KEY (`user_id`)
            REFERENCES `internet_shop`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE `internet_shop`.`shopping_carts_products`
(
    `id`         BIGINT(11) NOT NULL AUTO_INCREMENT,
    `cart_id`    BIGINT(11) NOT NULL,
    `product_id` BIGINT(11) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `product_idx` (`product_id` ASC) VISIBLE,
    INDEX `cart_idx` (`cart_id` ASC) VISIBLE,
    CONSTRAINT `product_fk`
        FOREIGN KEY (`product_id`)
            REFERENCES `internet_shop`.`products` (`product_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `cart_fk`
        FOREIGN KEY (`cart_id`)
            REFERENCES `internet_shop`.`shopping_carts` (`cart_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
