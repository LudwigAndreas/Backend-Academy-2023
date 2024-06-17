
CREATE SCHEMA IF NOT EXISTS domain;

CREATE DOMAIN domain.phone_number AS text
    CHECK (VALUE ~ '^(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]?\d{3}[\s.-]?\d{4}$');

CREATE DOMAIN domain.email AS text
    CHECK (VALUE ~ '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$');

CREATE DOMAIN domain.username AS text
    CHECK (VALUE ~ '^[a-zA-Z0-9._-]{3,}$');

-- CREATE DOMAIN iol.domain.password AS text
--     CHECK (VALUE ~ '^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$');


CREATE TABLE IF NOT EXISTS users
(
    id         UUID PRIMARY KEY,
    first_name text,
    last_name  text,
    middle_name text,
    login      domain.username UNIQUE,
    email      domain.email UNIQUE,
    phone      domain.phone_number UNIQUE,
    password   text NOT NULL,
    birth_date date,
    about      text,
    registered timestamp DEFAULT now() NOT NULL,
    last_login timestamp DEFAULT now() NOT NULL,
    is_active  boolean DEFAULT TRUE NOT NULL
);

