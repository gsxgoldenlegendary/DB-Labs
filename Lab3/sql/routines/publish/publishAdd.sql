USE trrs;
DELIMITER //
DROP PROCEDURE IF EXISTS publishAdd;
CREATE PROCEDURE publishAdd(
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

    IF NOT EXISTS(SELECT * FROM paper WHERE paper.id = p_id) THEN
        INSERT INTO paper(id, title, source, year, type, level)
        VALUES (p_id, p_title, p_source, p_year, p_type, p_level);
    ELSE
        IF NOT EXISTS(SELECT *
                      FROM paper
                      WHERE paper.id = p_id
                        AND paper.title = p_title
                        AND paper.source = p_source
                        AND paper.year = p_year
                        AND paper.type = p_type
                        AND paper.level = p_level) THEN
            SET s = 1;
        END IF;
    END IF;

    IF NOT EXISTS(SELECT * FROM teacher WHERE teacher.id = t_id) THEN
        SET s = 2;
    END IF;

    IF t_is_corresponding_author THEN
        IF EXISTS(SELECT * FROM publish WHERE publish.paper_id = p_id AND publish.is_corresponding_author = 1) THEN
            SET s = 3;
        END IF;
    END IF;

    IF EXISTS(SELECT * FROM publish WHERE publish.paper_id = p_id AND publish.ranking = t_ranking) THEN
        SET s = 4;
    END IF;

    IF EXISTS(SELECT * FROM publish WHERE publish.paper_id = p_id AND publish.teacher_id = t_id) THEN
        SET s = 5;
    END IF;

    IF s = 0 THEN
        INSERT INTO publish(paper_id, teacher_id, ranking, is_corresponding_author)
        VALUES (p_id, t_id, t_ranking, t_is_corresponding_author);
    ELSE
        CASE s
            WHEN 1 THEN SIGNAL SQLSTATE '45001'
                SET MESSAGE_TEXT = '该论文情况已经存在，但是信息不一致。';
            WHEN 2 THEN SIGNAL SQLSTATE '45002'
                SET MESSAGE_TEXT = '该教师不存在。';
            WHEN 3 THEN SIGNAL SQLSTATE '45003'
                SET MESSAGE_TEXT = '该论文已经有通讯作者。';
            WHEN 4 THEN SIGNAL SQLSTATE '45004'
                SET MESSAGE_TEXT = '该论文已经有该排名的作者。';
            WHEN 5 THEN SIGNAL SQLSTATE '45005'
                SET MESSAGE_TEXT = '该发表信息已经登记';
            END CASE;
    END IF;
END //
DELIMITER ;

#CALL publishAdd(2, 'paper2', 'source2', 2019, 2, 2, '1', 2, 0);