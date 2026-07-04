-- Schema definition for the H2 database.
-- Spring Boot picks this file up automatically from src/main/resources,
-- and runs it before data.sql.

CREATE TABLE IF NOT EXISTS greeting (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    language VARCHAR(255) NOT NULL,
    text     VARCHAR(1000) NOT NULL
);

CREATE TABLE IF NOT EXISTS app_user (
    id        INT AUTO_INCREMENT PRIMARY KEY,
     name      VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    language  VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS sales_banner (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    language    VARCHAR(255) NOT NULL,
    headline    VARCHAR(500) NOT NULL,
    cta_label   VARCHAR(255) NOT NULL,
    picture_url VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS product (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    picture_url VARCHAR(1000),
    price       DOUBLE NOT NULL
);
