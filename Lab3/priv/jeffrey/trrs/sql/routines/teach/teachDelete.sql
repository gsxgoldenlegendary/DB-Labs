USE trrs;
DELIMITER //
DROP PROCEDURE IF EXISTS teach_delete;
CREATE PROCEDURE teach_delete(
    IN c_id CHARACTER(255)
)
BEGIN
    DECLARE s INTEGER DEFAULT 0;

    IF NOT EXISTS(SELECT * FROM course WHERE course.id = c_id) THEN
        SET s = 1;
    END IF;


    IF s = 0 THEN
        DELETE FROM teach WHERE teach.course_id = c_id;
        DELETE FROM course WHERE course.id = c_id;
    ELSE
        CASE s
            WHEN 1 THEN SIGNAL SQLSTATE '45241'
                SET MESSAGE_TEXT = '该课程不存在。';
            END CASE;
    END IF;

END //
DELIMITER ;

#CALL publishDelete(2, '2');