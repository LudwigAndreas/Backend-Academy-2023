CREATE TABLE person
(
    id        BIGINT PRIMARY KEY,
    name      VARCHAR(255),
    about_me  TEXT,
    birthdate DATE
);

CREATE TABLE child
(
    parent_id BIGINT,
    child_id  BIGINT,
    FOREIGN KEY (parent_id) REFERENCES person (id),
    FOREIGN KEY (child_id) REFERENCES person (id)
);