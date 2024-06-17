CREATE TABLE IF NOT EXISTS student (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(255) CHECK (LENGTH(login) >= 3 AND LENGTH(login) <= 255) UNIQUE,
    first_name VARCHAR(255) CHECK (LENGTH(first_name) >= 1 AND LENGTH(first_name) <= 255),
    last_name VARCHAR(255) CHECK (LENGTH(last_name) >= 1 AND LENGTH(last_name) <= 255),
    age INTEGER CHECK (age >= 1 AND age <= 150)
);