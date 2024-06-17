CREATE TABLE IF NOT EXISTS course (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) CHECK (LENGTH(name) >= 1 AND LENGTH(name) <= 255) UNIQUE,
    description VARCHAR(255) CHECK (LENGTH(description) <= 255),
    duration INTEGER CHECK (duration >= 1) NOT NULL
);