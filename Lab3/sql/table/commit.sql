USE trrs;

CREATE TABLE commit
(
    project_id     CHARACTER(255),
    teacher_id     CHARACTER(5),
    ranking        INTEGER,
    commit_funding FLOAT,

    CONSTRAINT afford_pk PRIMARY KEY (project_id, teacher_id),
    CONSTRAINT afford_project_fk FOREIGN KEY (project_id) REFERENCES project (id),
    CONSTRAINT afford_teacher_fk FOREIGN KEY (teacher_id) REFERENCES teacher (id)
);