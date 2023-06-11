USE trrs;

CREATE TABLE course
(
    id       CHARACTER(255),
    name     CHARACTER(255),
    hours    INTEGER,
    property INTEGER,

    CONSTRAINT course_pk PRIMARY KEY (id)
);

CREATE TABLE teach
(
    course_id    CHARACTER(255),
    teacher_id   CHARACTER(5),
    year         INTEGER,
    semester     INTEGER,
    commit_hours INTEGER,

    CONSTRAINT teach_pk PRIMARY KEY (course_id, teacher_id, year, semester),
    CONSTRAINT teach_course_fk FOREIGN KEY (course_id) REFERENCES course (id),
    CONSTRAINT teach_teacher_fk FOREIGN KEY (teacher_id) REFERENCES teacher (id)
);

DELIMITER //
DROP PROCEDURE IF EXISTS courseAdd//
CREATE PROCEDURE courseAdd(
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
        SIGNAL SQLSTATE '45200' SET MESSAGE_TEXT = '课程信息不匹配';
    END IF;
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS courseUpdate//
CREATE PROCEDURE courseUpdate(
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
            SET course.name     = courseName,
                course.hours    = courseHours,
                course.property = courseProperty
            WHERE course.id = courseId;
        END IF;
    END IF;

    IF s = 1 THEN
        SIGNAL SQLSTATE '45201' SET MESSAGE_TEXT = '该课程不存在，无法更新。';
    END IF;
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS teachAdd;
CREATE PROCEDURE teachAdd(
    IN teachCourseId CHARACTER(255),
    IN teachTeacherId CHARACTER(5),
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

    IF EXISTS(SELECT *
              FROM teach
              WHERE teach.teacher_id = teachTeacherId
                AND teach.course_id = teachCourseId
                AND teach.year = teachYear
                AND teach.semester = teachSemester) THEN
        SET s = 3;
    END IF;

    IF s = 0 THEN
        INSERT INTO teach(course_id, teacher_id, year, semester, commit_hours)
        VALUES (teachCourseId, teachTeacherId, teachYear, teachSemester, teachCommitHours);
    ELSE
        CASE s
            WHEN 1 THEN SET @message_text = CONCAT('教师 ', CAST(teachRank AS CHAR), ' 不存在。');
                        SIGNAL SQLSTATE '45202' SET MESSAGE_TEXT = @message_text;
            WHEN 2 THEN SIGNAL SQLSTATE '45203' SET MESSAGE_TEXT = '课程不存在。';
            WHEN 3 THEN SIGNAL SQLSTATE '45204' SET MESSAGE_TEXT = '该授课记录已经存在。';
            END CASE;
    END IF;
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS teachUpdate;
CREATE PROCEDURE teachUpdate(
    IN teachCourseId CHARACTER(255),
    IN teachTeacherId CHARACTER(5),
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

    IF NOT EXISTS(SELECT * FROM teach WHERE teach.teacher_id = teachTeacherId AND teach.course_id = teachCourseId) THEN
        SET s = 3;
    END IF;

    IF s = 0 THEN
        UPDATE teach
        SET
             teach.commit_hours = teachCommitHours
        WHERE teach.teacher_id = teachTeacherId
          AND teach.course_id = teachCourseId
          AND teach.year = teachYear
            AND teach.semester = teachSemester;
    ELSE
        CASE s
            WHEN 1 THEN SET @message_text = CONCAT('教师 ', CAST(teachRank AS CHAR), ' 不存在。');
                        SIGNAL SQLSTATE '45205' SET MESSAGE_TEXT = @message_text;
            WHEN 2 THEN SIGNAL SQLSTATE '45206' SET MESSAGE_TEXT = '课程不存在。';
            WHEN 3 THEN SIGNAL SQLSTATE '45207' SET MESSAGE_TEXT = '该授课记录不存在。';
            END CASE;
    END IF;
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS teachDelete;
CREATE PROCEDURE teachDelete(
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
