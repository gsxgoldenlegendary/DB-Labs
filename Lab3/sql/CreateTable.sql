CREATE TABLE paper
(
    id     INTEGER,
    title  CHARACTER(255),
    source CHARACTER(255),
    year   DATE,
    type   INTEGER,
    level  INTEGER,

    CONSTRAINT paper_pk PRIMARY KEY (id)
);

CREATE TABLE teacher
(
    id     CHARACTER(5),
    name   CHARACTER(255),
    gender INTEGER,
    title  INTEGER,

    CONSTRAINT teacher_pk PRIMARY KEY (id)
);

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

CREATE TABLE course
(
    id       CHARACTER(255),
    name     CHARACTER(255),
    hours    INTEGER,
    property INTEGER,

    CONSTRAINT course_pk PRIMARY KEY (id)
);

CREATE TABLE publish
(
    paper_id                INTEGER,
    teacher_id              CHARACTER(5),
    ranking                 INTEGER,
    is_corresponding_author BOOLEAN,

    CONSTRAINT publish_pk PRIMARY KEY (paper_id, teacher_id),
    CONSTRAINT publish_paper_fk FOREIGN KEY (paper_id) REFERENCES paper (id),
    CONSTRAINT publish_teacher_fk FOREIGN KEY (teacher_id) REFERENCES teacher (id)
);

CREATE TABLE afford
(
    project_id     CHARACTER(255),
    teacher_id     CHARACTER(5),
    ranking        INTEGER,
    afford_funding FLOAT,

    CONSTRAINT afford_pk PRIMARY KEY (project_id, teacher_id),
    CONSTRAINT afford_project_fk FOREIGN KEY (project_id) REFERENCES project (id),
    CONSTRAINT afford_teacher_fk FOREIGN KEY (teacher_id) REFERENCES teacher (id)
);

CREATE TABLE teach
(
    course_id    CHARACTER(255),
    teacher_id   CHARACTER(5),
    year         INTEGER,
    semester     INTEGER,
    afford_hours INTEGER,

    CONSTRAINT teach_pk PRIMARY KEY (course_id, teacher_id),
    CONSTRAINT teach_course_fk FOREIGN KEY (course_id) REFERENCES course (id),
    CONSTRAINT teach_teacher_fk FOREIGN KEY (teacher_id) REFERENCES teacher (id)
);
