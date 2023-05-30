USE trrs;
DELIMITER //
DROP PROCEDURE IF EXISTS teach_update;
CREATE PROCEDURE teach_update(
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

    IF s = 0 THEN
        UPDATE teach
        SET teach.year = teachYear
            AND teach.semester = teachSemester
            AND teach.commit_hours = teachCommitHours
        WHERE teach.teacher_id = teachTeacherId
          AND teach.course_id = teachCourseId;
    ELSE
        CASE s
            WHEN 1 THEN SET @message_text = CONCAT('教师 ', CAST(teachRank AS CHAR), ' 不存在。');
                        SIGNAL SQLSTATE '45210' SET MESSAGE_TEXT = @message_text;
            WHEN 2 THEN SIGNAL SQLSTATE '45211' SET MESSAGE_TEXT = '课程不存在。';
            END CASE;
    END IF;

END //
DELIMITER ;
