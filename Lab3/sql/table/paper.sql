USE trrs;
DROP TABLE IF EXISTS paper;
CREATE TABLE paper
(
    id     INTEGER,
    title  CHARACTER(255),
    source CHARACTER(255),
    date   DATE,
    type   INTEGER,
    level  INTEGER,

    CONSTRAINT paper_pk PRIMARY KEY (id)
);
