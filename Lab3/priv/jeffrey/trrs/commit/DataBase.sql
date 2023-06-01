USE trrs;

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

DELIMITER //
DROP PROCEDURE IF EXISTS projectAdd;
CREATE PROCEDURE projectAdd(
    IN p_id CHARACTER(255),
    IN p_name CHARACTER(255),
    IN p_source CHARACTER(255),
    IN p_type INTEGER,
    IN p_expense FLOAT,
    IN p_start_year INTEGER,
    IN p_end_year INTEGER
)
BEGIN
    DECLARE s INTEGER DEFAULT 0;

    IF NOT EXISTS(SELECT * FROM project WHERE id = p_id) THEN
        INSERT INTO project(id, name, source, type, funding, start_year, end_year)
        VALUES (p_id, p_name, p_source, p_type, p_expense, p_start_year, p_end_year);
    ELSE
        IF NOT EXISTS(SELECT *
                      FROM project
                      WHERE id = p_id
                        AND name = p_name
                        AND source = p_source
                        AND type = p_type
                        AND funding = p_expense
                        AND start_year = p_start_year
                        AND end_year = p_end_year) THEN
            SET s = 1;
        END IF;
    END IF;

    IF s = 1 THEN SIGNAL SQLSTATE '45101' SET MESSAGE_TEXT = '该项目已存在，但是信息不一致'; END IF;
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS projectUpdate;
CREATE PROCEDURE projectUpdate(
    IN p_id CHARACTER(255),
    IN p_name CHARACTER(255),
    IN p_source CHARACTER(255),
    IN p_type INTEGER,
    IN p_expense FLOAT,
    IN p_start_year INTEGER,
    IN p_end_year INTEGER
)
BEGIN
    DECLARE s INTEGER DEFAULT 0;

    IF NOT EXISTS(SELECT * FROM project WHERE id = p_id) THEN
        SET s = 1;
    ELSE
        IF NOT EXISTS(SELECT *
                      FROM project
                      WHERE id = p_id
                        AND name = p_name
                        AND source = p_source
                        AND type = p_type
                        AND funding = p_expense
                        AND start_year = p_start_year
                        AND end_year = p_end_year) THEN
            UPDATE project
            SET name       = p_name,
                source     = p_source,
                type       = p_type,
                funding    = p_expense,
                start_year = p_start_year,
                end_year   = p_end_year
            WHERE id = p_id;
        END IF;
    END IF;

    IF s = 1 THEN
        SIGNAL SQLSTATE '45121' SET MESSAGE_TEXT = '该项目不存在，无法修改。';
    END IF;
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS commitAdd;
CREATE PROCEDURE commitAdd(
    IN p_id CHARACTER(255),
    IN t_id CHARACTER(5),
    IN t_rank INTEGER,
    IN t_expense FLOAT
)
BEGIN
    DECLARE s INTEGER DEFAULT 0;

    IF NOT EXISTS(SELECT * FROM project WHERE project.id = p_id) THEN
        SET s = 1;
    END IF;

    IF NOT EXISTS(SELECT * FROM teacher WHERE teacher.id = t_id) THEN
        SET s = 2;
    END IF;


    IF EXISTS(SELECT * FROM commit WHERE commit.project_id = p_id AND commit.teacher_id = t_id) THEN
        SET s = 3;
    END IF;

    IF s = 0 THEN
        INSERT INTO commit(project_id, teacher_id, ranking, commit_funding)
        VALUES (p_id, t_id, t_rank, t_expense);
    ELSE
        CASE s
            WHEN 1 THEN SIGNAL SQLSTATE '45101' SET MESSAGE_TEXT = '项目不存在。';
            WHEN 2 THEN SET @msg = CONCAT('教师 ', CAST(t_rank AS CHAR), ' 不存在。');
                        SIGNAL SQLSTATE '45102' SET MESSAGE_TEXT = @msg;
            WHEN 3 THEN SET @msg = CONCAT('教师 ', CAST(t_rank AS CHAR), '已经参与该项目。');
                        SIGNAL SQLSTATE '45103' SET MESSAGE_TEXT = @msg;
            END CASE;
    END IF;

END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS commitUpdate;
CREATE PROCEDURE commitUpdate(
    IN p_id CHARACTER(255),
    IN t_id CHARACTER(5),
    IN t_rank INTEGER,
    IN t_expense FLOAT
)
BEGIN
    DECLARE s INTEGER DEFAULT 0;

    IF NOT EXISTS(SELECT * FROM project WHERE project.id = p_id) THEN
        SET s = 1;
    END IF;

    IF NOT EXISTS(SELECT * FROM teacher WHERE teacher.id = t_id) THEN
        SET s = 2;
    END IF;

    IF s = 0 THEN
        UPDATE commit
        SET commit.commit_funding = t_expense
            AND commit.ranking = t_rank
        WHERE commit.project_id = p_id
          AND commit.teacher_id = t_id;
    ELSE
        CASE s
            WHEN 1 THEN SIGNAL SQLSTATE '45101' SET MESSAGE_TEXT = '项目不存在。';
            WHEN 2 THEN SET @msg = CONCAT('教师 ', CAST(t_rank AS CHAR), ' 不存在。');
                        SIGNAL SQLSTATE '45102' SET MESSAGE_TEXT = @msg;
            END CASE;
    END IF;

END //
DELIMITER ;


DELIMITER //
DROP PROCEDURE IF EXISTS commitDelete;
CREATE PROCEDURE commitDelete(
    IN p_id CHARACTER(255)
)
BEGIN
    DECLARE s INTEGER DEFAULT 0;

    IF NOT EXISTS(SELECT * FROM project WHERE project.id = p_id) THEN
        SET s = 1;
    END IF;


    IF s = 0 THEN
        DELETE FROM commit WHERE commit.project_id = p_id;
        DELETE FROM project WHERE project.id = p_id;
    ELSE
        CASE s
            WHEN 1 THEN SIGNAL SQLSTATE '45141'
                SET MESSAGE_TEXT = '该项目不存在。';
            END CASE;
    END IF;

END //
DELIMITER ;