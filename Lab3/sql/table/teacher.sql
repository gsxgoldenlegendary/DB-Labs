USE trrs;
DROP TABLE IF EXISTS teacher;
CREATE TABLE teacher
(
    id     CHARACTER(5),
    name   CHARACTER(255),
    gender INTEGER,
    title  INTEGER,

    CONSTRAINT teacher_pk PRIMARY KEY (id)
);