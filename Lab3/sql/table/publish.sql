DROP TABLE IF EXISTS publish;
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