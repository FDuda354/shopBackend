--liquibase formatted sql
--changeset FDuda:15
CREATE TABLE users (
id serial PRIMARY KEY,
username varchar(50) NOT NULL UNIQUE,
password varchar(500) NOT NULL,
enabled boolean NOT NULL
);
--changeset FDuda:16
CREATE TABLE authorities (
username varchar(50) NOT NULL,
authority varchar(50) NOT NULL,
CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
);
--changeset FDuda:17
CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);
--changeset FDuda:18
INSERT INTO users (id, username, password, enabled)
VALUES (1, 'admin', '{bcrypt}$2a$10$upzXFsFUOClFRR69OMKF8eajGMRs0vhcSHqvNDKy9yfW45w7o9z6O', true);
INSERT INTO authorities (username, authority)
VALUES ('admin', 'ROLE_ADMIN');
