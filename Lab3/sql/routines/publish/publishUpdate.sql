USE trrs;
DELIMITER //
DROP PROCEDURE IF EXISTS publishUpdate;
CREATE PROCEDURE publishUpdate(
    IN p_id INTEGER,
    IN p_title CHARACTER(255),
    IN p_source CHARACTER(255),
    IN p_year INTEGER,
    IN p_type INTEGER,
    IN p_level INTEGER,
    IN t_id CHARACTER(5),
    IN t_ranking INTEGER,
    IN t_is_corresponding_author BOOLEAN
)
BEGIN
    DECLARE s INTEGER DEFAULT 0;

    IF NOT EXISTS(SELECT * FROM publish WHERE publish.paper_id = p_id AND publish.teacher_id = t_id) THEN
        SET s = 1;
    END IF;

    IF t_is_corresponding_author
        AND EXISTS(SELECT *
                   FROM publish
                   WHERE publish.paper_id = p_id
                     AND publish.teacher_id != t_id
                     AND publish.is_corresponding_author = 1) THEN
        SET s = 2;
    END IF;

    IF EXISTS(SELECT *
              FROM publish
              WHERE publish.paper_id = p_id
                AND publish.teacher_id != t_id
                AND publish.ranking = t_ranking) THEN
        SET s = 3;
    END IF;

    START TRANSACTION;
    UPDATE paper
    SET paper.title  = p_title,
        paper.source = p_source,
        paper.year   = p_year,
        paper.type   = p_type,
        paper.level  = p_level
    WHERE paper.id = p_id;

    UPDATE publish
    SET publish.ranking                 = t_ranking,
        publish.is_corresponding_author = t_is_corresponding_author
    WHERE publish.paper_id = p_id
      AND publish.teacher_id = t_id;

    IF s = 0 THEN
        COMMIT;
    ELSE
        CASE s
            WHEN 1 THEN SIGNAL SQLSTATE '45021'
                SET MESSAGE_TEXT = '该发表情况不存在，无需更新。';
            WHEN 2 THEN SIGNAL SQLSTATE '45022'
                SET MESSAGE_TEXT = '该论文已有通讯作者。';
            WHEN 3 THEN SIGNAL SQLSTATE '45023'
                SET MESSAGE_TEXT = '该论文已有该排名的作者。';
            END CASE;
        ROLLBACK;
    END IF;
END //
DELIMITER ;

#CALL publishUpdate(2, 'paper3', 'source2', 2019, 2, 2, '1', 3, 0);

