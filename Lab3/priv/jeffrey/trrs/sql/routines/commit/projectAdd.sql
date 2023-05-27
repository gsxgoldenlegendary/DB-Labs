USE trrs;
DELIMITER //
DROP PROCEDURE IF EXISTS projectAdd;
CREATE PROCEDURE projectAdd(
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
        INSERT INTO project(id, name, source, type, funding, start_year, end_year)
        VALUES (p_id, p_name, p_source, p_type, p_expense, p_start_year, p_end_year);
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
            SET s = 1;
        END IF;
    END IF;

    IF s=1 THEN SIGNAL SQLSTATE '45101' SET MESSAGE_TEXT = '该项目已存在，但是信息不一致'; END IF;
END //
DELIMITER ;