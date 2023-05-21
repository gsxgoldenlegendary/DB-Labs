DROP TABLE IF EXISTS course;
CREATE TABLE course
(
    id       CHARACTER(255),
    name     CHARACTER(255),
    hours    INTEGER,
    property INTEGER,

    CONSTRAINT course_pk PRIMARY KEY (id)
);
