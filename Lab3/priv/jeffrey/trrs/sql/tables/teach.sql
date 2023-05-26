USE trrs;

CREATE TABLE teach
(
    course_id    CHARACTER(255),
    teacher_id   CHARACTER(5),
    year         INTEGER,
    semester     INTEGER,
    commit_hours INTEGER,

    CONSTRAINT teach_pk PRIMARY KEY (course_id, teacher_id),
    CONSTRAINT teach_course_fk FOREIGN KEY (course_id) REFERENCES course (id),
    CONSTRAINT teach_teacher_fk FOREIGN KEY (teacher_id) REFERENCES teacher (id)
);
