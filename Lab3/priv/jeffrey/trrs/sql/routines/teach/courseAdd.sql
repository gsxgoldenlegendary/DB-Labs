USE trrs;
DELIMITER //
DROP PROCEDURE IF EXISTS course_add//
CREATE PROCEDURE course_add(
    IN courseId CHARACTER(255),
    IN courseName CHARACTER(255),
    IN courseHours INTEGER,
    IN courseProperty INTEGER
)
BEGIN
    DECLARE s INTEGER DEFAULT 0;

    IF NOT EXISTS(SELECT * FROM course WHERE course.id = courseId) THEN
        INSERT INTO course(id, name, hours, property)
        VALUES (courseId, courseName, courseHours, courseProperty);
    ELSE
        IF NOT EXISTS(SELECT *
                      FROM course
                      WHERE course.id = courseId
                        AND course.name = courseName
                        AND course.hours = courseHours
                        AND course.property = courseProperty) THEN
            SET s = 1;
        END IF;
    END IF;

    IF s = 1 THEN
        SIGNAL SQLSTATE '45201' SET MESSAGE_TEXT = '课程信息不匹配';
    END IF;
END //
DELIMITER ;
