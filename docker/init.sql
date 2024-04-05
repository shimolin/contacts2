CREATE SCHEMA IF NOT EXISTS contacts_schema;

CREATE TABLE IF NOT EXISTS contacts_schema.contacts
(
    id BIGINT PRIMARY KEY,
    firstname varchar(255) NOT NULL,
    lastname varchar(255) NOT NULL,
    email varchar(127) NOT NULL,
    phone varchar(15) NOT NULL
)



