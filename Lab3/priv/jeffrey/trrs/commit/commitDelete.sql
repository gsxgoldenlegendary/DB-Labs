USE trrs;
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

#CALL publishDelete(2, '2');