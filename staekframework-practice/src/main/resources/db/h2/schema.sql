-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

DROP TABLE users IF EXISTS;

CREATE TABLE users (
    id INTEGER PRIMARY KEY,
    name VARCHAR(10) NOT NULL,
    password VARCHAR(64) NOT NULL,
    price INTEGER NOT NULL DEFAULT 0
);