USE trrs;
DELIMITER //
DROP PROCEDURE IF EXISTS projectUpdate;
CREATE PROCEDURE projectUpdate(
    IN p_id CHARACTER(255),
    IN p_name CHARACTER(255),
    IN p_source CHARACTER(255),
    IN p_type INTEGER,
    IN p_expense FLOAT,
    IN p_start_year INTEGER,
    IN p_end_year INTEGER
)
BEGIN
    DECLARE s INTEGER DEFAULT 0;

    IF NOT EXISTS(SELECT * FROM project WHERE id = p_id) THEN
        SET s = 1;
    ELSE
        IF NOT EXISTS(SELECT *
                      FROM project
                      WHERE id = p_id
                        AND name = p_name
                        AND source = p_source
                        AND type = p_type
                        AND funding = p_expense
                        AND start_year = p_start_year
                        AND end_year = p_end_year) THEN
            UPDATE project
            SET name       = p_name,
                source     = p_source,
                type       = p_type,
                funding    = p_expense,
                start_year = p_start_year,
                end_year   = p_end_year
            WHERE id = p_id;
        END IF;
    END IF;

    IF s = 1 THEN
        SIGNAL SQLSTATE '45121' SET MESSAGE_TEXT = '该项目不存在，无法修改。';
    END IF;
END //
DELIMITER ;