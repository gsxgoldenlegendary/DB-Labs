DROP TABLE IF EXISTS project;
CREATE TABLE project
(
    id         CHARACTER(255),
    name       CHARACTER(255),
    source     CHARACTER(255),
    type       INTEGER,
    funding    FLOAT,
    start_year INTEGER,
    end_year   INTEGER,

    CONSTRAINT project_pk PRIMARY KEY (id)
);
