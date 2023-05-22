USE trrs;
DELIMITER //
DROP PROCEDURE IF EXISTS publish_add;
CREATE PROCEDURE publish_add(
    IN p_id INTEGER,
    IN p_title CHARACTER(255),
    IN p_source CHARACTER(255),
    IN p_year INTEGER,
    IN p_type INTEGER,
    IN p_level INTEGER,
    IN t_id CHARACTER(5),
    IN p_ranking INTEGER,
    IN p_is_corresponding_author BOOLEAN
)
BEGIN
    DECLARE s INTEGER DEFAULT 0;

    IF NOT EXISTS(SELECT COUNT(*) FROM paper WHERE paper.id = p_id) THEN
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

    IF p_is_corresponding_author THEN
        IF EXISTS(SELECT * FROM publish WHERE publish.paper_id = p_id AND publish.is_corresponding_author = 1) THEN
            SET s = 3;
        END IF;
    END IF;

    IF EXISTS(SELECT * FROM publish WHERE publish.paper_id = p_id AND publish.ranking = p_ranking) THEN
        SET s = 4;
    END IF;

    CASE s
        WHEN 0 THEN INSERT INTO publish(paper_id, teacher_id, ranking, is_corresponding_author)
                    VALUES (p_id, t_id, p_ranking, p_is_corresponding_author);
        WHEN 1 THEN SIGNAL SQLSTATE '45001'
            SET MESSAGE_TEXT = 'Paper has already existed and the information is different.';
        WHEN 2 THEN SIGNAL SQLSTATE '45002'
            SET MESSAGE_TEXT = 'Teacher does not exist.';
        WHEN 3 THEN SIGNAL SQLSTATE '45003'
            SET MESSAGE_TEXT = 'Corresponding author has already existed.';
        WHEN 4 THEN SIGNAL SQLSTATE '45004'
            SET MESSAGE_TEXT = 'Ranking has already existed.';
        END CASE;
END //
DELIMITER ;