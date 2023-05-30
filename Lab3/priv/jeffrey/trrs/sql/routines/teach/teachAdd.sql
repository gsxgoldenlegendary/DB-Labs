USE trrs;
DELIMITER //
DROP PROCEDURE IF EXISTS teach_add;
CREATE PROCEDURE teach_add(
    IN teachCourseId INTEGER,
    IN teachTeacherId INTEGER,
    IN teachYear INTEGER,
    IN teachSemester INTEGER,
    IN teachCommitHours INTEGER,
    IN teachRank INTEGER)
BEGIN
    DECLARE s INTEGER DEFAULT 0;

    IF NOT EXISTS(SELECT * FROM teacher WHERE teacher.id = teachTeacherId) THEN
        SET s = 1;
    END IF;

    IF NOT EXISTS(SELECT * FROM course WHERE course.id = teachCourseId) THEN
        SET s = 2;
    END IF;

    IF EXISTS(SELECT * FROM teach WHERE teach.teacher_id = teachTeacherId AND teach.course_id = teachCourseId) THEN
        SET s = 3;
    END IF;

    IF s = 0 THEN
        INSERT INTO teach(course_id, teacher_id, year, semester, commit_hours)
        VALUES(teachCourseId, teachTeacherId, teachYear, teachSemester, teachCommitHours);
    ELSE
        CASE s
            WHEN 1 THEN
                SET @message_text = CONCAT('教师 ', CAST(teachRank AS CHAR), ' 不存在。');
                SIGNAL SQLSTATE '45210' SET MESSAGE_TEXT = @message_text;
            WHEN 2 THEN SIGNAL SQLSTATE '45211' SET MESSAGE_TEXT = '课程不存在。';
            WHEN 3 THEN SIGNAL SQLSTATE '45212' SET MESSAGE_TEXT = '该授课记录已经存在。';
        END CASE;
        END IF;

END //
DELIMITER ;
