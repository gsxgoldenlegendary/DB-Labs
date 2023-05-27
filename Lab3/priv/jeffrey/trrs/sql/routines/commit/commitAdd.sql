USE trrs;
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
