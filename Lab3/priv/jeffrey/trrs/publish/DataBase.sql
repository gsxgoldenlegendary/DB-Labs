USE trrs;

CREATE TABLE paper
(
    id     INTEGER,
    title  CHARACTER(255),
    source CHARACTER(255),
    year   INTEGER,
    type   INTEGER,
    level  INTEGER,

    CONSTRAINT paper_pk PRIMARY KEY (id)
);

CREATE TABLE publish
(
    paper_id                INTEGER,
    teacher_id              CHARACTER(5),
    ranking                 INTEGER,
    is_corresponding_author BOOLEAN,

    CONSTRAINT publish_pk PRIMARY KEY (paper_id, teacher_id),
    CONSTRAINT publish_paper_fk FOREIGN KEY (paper_id) REFERENCES paper (id),
    CONSTRAINT publish_teacher_fk FOREIGN KEY (teacher_id) REFERENCES teacher (id)
);

DELIMITER //
DROP PROCEDURE IF EXISTS paperAdd;
CREATE PROCEDURE paperAdd(
    IN paperId INTEGER,
    IN paperTitle CHARACTER(255),
    IN paperSource CHARACTER(255),
    IN paperYear INTEGER,
    IN paperType INTEGER,
    IN paperLevel INTEGER
)
BEGIN
    DECLARE s INTEGER DEFAULT 0;

    IF NOT EXISTS (SELECT * FROM paper WHERE id = paperId) THEN
        INSERT INTO paper VALUES (paperId, paperTitle, paperSource, paperYear, paperType, paperLevel);
    ELSE
        IF NOT EXISTS (SELECT *
                       FROM paper
                       WHERE id = paperId
                         AND title = paperTitle
                         AND source = paperSource
                         AND year = paperYear
                         AND type = paperType) THEN
            SET s = 1;
        END IF;
    END IF;

    IF s = 1 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '该论文已存在，但信息不匹配。';
    END IF;
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS paperUpdate;
CREATE PROCEDURE paperUpdate(
    IN paperId INTEGER,
    IN paperTitle CHARACTER(255),
    IN paperSource CHARACTER(255),
    IN paperYear INTEGER,
    IN paperType INTEGER,
    IN paperLevel INTEGER
)
BEGIN
    DECLARE s INTEGER DEFAULT 0;

    IF NOT EXISTS (SELECT * FROM paper WHERE id = paperId) THEN
        SET s = 1;
    ELSE
        IF NOT EXISTS (SELECT *
                       FROM paper
                       WHERE id = paperId
                         AND title = paperTitle
                         AND source = paperSource
                         AND year = paperYear
                         AND type = paperType) THEN
            UPDATE paper
            SET title  = paperTitle,
                source = paperSource,
                year   = paperYear,
                type   = paperType,
                level  = paperLevel
            WHERE id = paperId;
        END IF;
    END IF;

    IF s = 1 THEN
        SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = '该论文不存在，无法更新。';
    END IF;
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS publishAdd;
CREATE PROCEDURE publishAdd(
    IN paperId INTEGER,
    IN teacherId CHARACTER(5),
    IN publishRanking INTEGER,
    IN publishIsCorrespondingAuthor BOOLEAN,
    IN publishRank INTEGER
)
BEGIN
    DECLARE s INTEGER DEFAULT 0;

    IF NOT EXISTS(SELECT * FROM paper WHERE paper.id = paperId) THEN
        SET s = 1;
    END IF;

    IF NOT EXISTS(SELECT * FROM teacher WHERE teacher.id = teacherId) THEN
        SET s = 2;
    END IF;

    IF publishIsCorrespondingAuthor THEN
        IF EXISTS(SELECT * FROM publish WHERE publish.paper_id = paperId AND publish.is_corresponding_author = 1) THEN
            SET s = 3;
        END IF;
    END IF;

    IF EXISTS(SELECT * FROM publish WHERE publish.paper_id = paperId AND publish.ranking = publishRanking) THEN
        SET s = 4;
    END IF;

    IF EXISTS(SELECT * FROM publish WHERE publish.paper_id = paperId AND publish.teacher_id = teacherId) THEN
        SET s = 5;
    END IF;

    IF s = 0 THEN
        INSERT INTO publish(paper_id, teacher_id, ranking, is_corresponding_author)
        VALUES (paperId, teacherId, publishRanking, publishIsCorrespondingAuthor);
    ELSE
        CASE s
            WHEN 1 THEN SIGNAL SQLSTATE '45002'
                SET MESSAGE_TEXT = '该论文情况已经存在，但是信息不一致。';
            WHEN 2 THEN SET @message_text = CONCAT('教师 ', CAST(publishRank AS CHAR), ' 不存在。');
                        SIGNAL SQLSTATE '45003' SET MESSAGE_TEXT = @message_text;
            WHEN 3 THEN SIGNAL SQLSTATE '45004'
                SET MESSAGE_TEXT = '该论文已经有通讯作者。';
            WHEN 4 THEN SIGNAL SQLSTATE '45005'
                SET MESSAGE_TEXT = '该论文已经有该排名的作者。';
            WHEN 5 THEN SIGNAL SQLSTATE '45006'
                SET MESSAGE_TEXT = '该发表信息已经登记';
            END CASE;
    END IF;
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS publishUpdate;
CREATE PROCEDURE publishUpdate(
    IN paperId INTEGER,
    IN teacherId CHARACTER(5),
    IN publishRanking INTEGER,
    IN publishIsCorrespondingAuthor BOOLEAN,
    IN publishRank INTEGER
)
BEGIN
    DECLARE s INTEGER DEFAULT 0;

    IF NOT EXISTS(SELECT * FROM teacher WHERE id = teacherId) THEN
        SET s = 1;
    END IF;

    IF NOT EXISTS(SELECT * FROM paper WHERE id = paperId) THEN
        SET s = 2;
    END IF;

    IF publishIsCorrespondingAuthor
        AND EXISTS(SELECT *
                   FROM publish
                   WHERE paper_id = paperId
                     AND is_corresponding_author = 1
                     AND teacher_id != teacherId) THEN
        SET s = 3;
    END IF;

    IF EXISTS(SELECT *
              FROM publish
              WHERE paper_id = paperId
                AND ranking = publishRanking
                AND teacher_id != teacherId) THEN
        SET s = 4;
    END IF;

    IF NOT EXISTS(SELECT * FROM publish WHERE paper_id = paperId AND teacher_id = teacherId) THEN
        SET s = 5;
    END IF;

    IF s = 0 THEN
        UPDATE publish
        SET ranking                 = publishRanking,
            is_corresponding_author = publishIsCorrespondingAuthor
        WHERE paper_id = paperId
          AND teacher_id = teacherId;
    ELSE
        CASE s
            WHEN 1 THEN SET @message_text = CONCAT('教师 ', CAST(publishRank AS CHAR), ' 不存在。');
                        SIGNAL SQLSTATE '45007' SET MESSAGE_TEXT = @message_text;
            WHEN 2 THEN SIGNAL SQLSTATE '45008' SET MESSAGE_TEXT = '论文不存在。';
            WHEN 3 THEN SIGNAL SQLSTATE '45009' SET MESSAGE_TEXT = '该论文已经有通讯作者。';
            WHEN 4 THEN SIGNAL SQLSTATE '45010' SET MESSAGE_TEXT = '该论文已经有该排名的作者。';
            WHEN 5 THEN SIGNAL SQLSTATE '45011' SET MESSAGE_TEXT = '该发表信息不存在。';
            END CASE;
    END IF;
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS publishDelete;
CREATE PROCEDURE publishDelete(
    IN paperId INTEGER
)
BEGIN
    DECLARE s INTEGER DEFAULT 0;

    IF NOT EXISTS(SELECT * FROM paper WHERE paper.id = paperId) THEN
        SET s = 1;
    END IF;

    IF s = 0 THEN
        DELETE FROM publish WHERE publish.paper_id = paperId;
        DELETE FROM paper WHERE paper.id = paperId;
    ELSE
        CASE s
            WHEN 1 THEN SIGNAL SQLSTATE '45012'
                SET MESSAGE_TEXT = '该发表情况不存在。';
            END CASE;
    END IF;
END //
DELIMITER ;
