USE trrs;
DELIMITER //
DROP PROCEDURE IF EXISTS course_update//
CREATE PROCEDURE course_update(
    IN courseId CHARACTER(255),
    IN courseName CHARACTER(255),
    IN courseHours INTEGER,
    IN courseProperty INTEGER
)
BEGIN
    DECLARE s INTEGER DEFAULT 0;

    IF NOT EXISTS(SELECT * FROM course WHERE course.id = courseId) THEN
        SET s = 1;
    ELSE
        IF NOT EXISTS(SELECT *
                      FROM course
                      WHERE course.id = courseId
                        AND course.name = courseName
                        AND course.hours = courseHours
                        AND course.property = courseProperty) THEN
           UPDATE course
              SET course.name = courseName,
                course.hours = courseHours,
                course.property = courseProperty
              WHERE course.id = courseId;
        END IF;
    END IF;

    IF s = 1 THEN
        SIGNAL SQLSTATE '45220' SET MESSAGE_TEXT = '该课程不存在，无法更新。';
    END IF;
END //
DELIMITER ;
