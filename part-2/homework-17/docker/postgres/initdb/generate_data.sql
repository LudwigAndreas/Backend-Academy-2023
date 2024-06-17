INSERT INTO person(id, name, about_me, birthdate)
SELECT generate_series(1, 1000000)                                    AS id,
       'Name ' || generate_series(1, 1000000)                         AS name,
       'ShortDesc'                                                    AS about_me,
       '2000-01-01'::date + (random() * 365)::int * '1 day'::interval AS birthdate;

INSERT INTO person(id, name, about_me, birthdate)
SELECT generate_series(1000001, 2000000)                              AS id,
       'Name ' || generate_series(1000001, 2000000)                   AS name,
       repeat('LongDescription', 100)                                 AS about_me,
       '2000-01-01'::date + (random() * 365)::int * '1 day'::interval AS birthdate;


INSERT INTO child(parent_id, child_id)
SELECT parent.id AS parent_id,
       child.id  AS child_id
FROM (SELECT id FROM person WHERE id <= 500000) AS parent,
     (SELECT id FROM person WHERE id > 500000 AND id <= 1000000 ORDER BY random() LIMIT 1) AS child
LIMIT 500000;


INSERT INTO child(parent_id, child_id)
SELECT parent.id AS parent_id,
       child.id  AS child_id
FROM (SELECT id FROM person WHERE id > 1000000 AND id <= 1020000) AS parent,
     (SELECT id FROM person WHERE id > 1020000 ORDER BY random() LIMIT 5) AS child;