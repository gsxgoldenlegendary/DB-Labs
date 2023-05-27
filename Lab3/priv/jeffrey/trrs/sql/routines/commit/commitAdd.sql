USE trrs;
DELIMITER //
DROP PROCEDURE IF EXISTS commityAdd;
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

    IF s = 0 THEN
        INSERT INTO commit(project_id, teacher_id, ranking, commit_funding)
        VALUES (p_id, t_id, t_rank, t_expense);
    ELSE
        CASE s
            WHEN 1 THEN SIGNAL SQLSTATE '45101' SET MESSAGE_TEXT = '项目不存在。';
            WHEN 2 THEN SIGNAL SQLSTATE '45102' SET MESSAGE_TEXT = CONCAT('教师', CAST(t_rank AS CHAR), '工号不存在。');
            END CASE;
    END IF;

END //
DELIMITER ;
