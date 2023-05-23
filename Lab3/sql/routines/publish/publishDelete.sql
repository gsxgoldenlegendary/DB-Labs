USE trrs;
DELIMITER //
DROP PROCEDURE IF EXISTS publishDelete;
CREATE PROCEDURE publishDelete(
    IN p_id INTEGER,
    IN t_id CHARACTER(5)
)
BEGIN
    DECLARE s INTEGER DEFAULT 0;

    IF NOT EXISTS(SELECT * FROM paper WHERE paper.id = p_id) THEN
        SET s = 1;
    END IF;

    IF NOT EXISTS(SELECT * FROM teacher WHERE teacher.id = t_id) THEN
        SET s = 2;
    END IF;

    IF NOT EXISTS(SELECT * FROM publish WHERE publish.paper_id = p_id AND publish.teacher_id = t_id) THEN
        SET s = 3;
    END IF;

    IF (SELECT COUNT(*) FROM publish WHERE publish.paper_id = p_id AND publish.teacher_id = t_id = 1) THEN
        SET s = 4;
    END IF;

    IF s = 4 THEN
        DELETE FROM publish WHERE publish.paper_id = p_id AND publish.teacher_id = t_id;
        DELETE FROM paper WHERE paper.id = p_id;
    ELSE
        IF s = 0 THEN
            DELETE FROM publish WHERE publish.paper_id = p_id AND publish.teacher_id = t_id;
        ELSE
            CASE s
                WHEN 1 THEN SIGNAL SQLSTATE '45011'
                    SET MESSAGE_TEXT = 'Paper not exists';
                WHEN 2 THEN SIGNAL SQLSTATE '45012'
                    SET MESSAGE_TEXT = 'Teacher not exists';
                WHEN 3 THEN SIGNAL SQLSTATE '45013'
                    SET MESSAGE_TEXT = 'Publication not exists';
                END CASE;
        END IF;
    END IF;
END //
DELIMITER ;

#CALL publishDelete(2, '2');