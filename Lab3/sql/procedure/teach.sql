USE trrs;
DELIMITER //
CREATE PROCEDURE teach_add(
    IN course_id_in INTEGER,
    IN teacher_id_in INTEGER,
    IN year_in INTEGER,
    IN semester_in INTEGER,
    IN commit_hours_in INTEGER)
BEGIN
    INSERT INTO teach (teach.course_id, teach.teacher_id, teach.year, teach.semester, teach.commit_hours)
    VALUES (course_id_in, teacher_id_in, year_in, semester_in, commit_hours_in);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE teach_drop(
    IN course_id_in INTEGER,
    IN teacher_id_in INTEGER,
    IN year_in INTEGER,
    IN semester_in INTEGER)
BEGIN
    DELETE FROM teach
    WHERE teach.course_id = course_id_in
      AND teach.teacher_id = teacher_id_in
      AND teach.year = year_in
      AND teach.semester = semester_in;

END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE teach_update(
    IN course_id_in INTEGER,
    IN teacher_id_in INTEGER,
    IN year_in INTEGER,
    IN semester_in INTEGER,
    IN commit_hours_in INTEGER)
BEGIN
    UPDATE teach
    SET teach.commit_hours = commit_hours_in
    WHERE teach.course_id = course_id_in
      AND teach.teacher_id = teacher_id_in
      AND teach.year = year_in
      AND teach.semester = semester_in;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE teach_add(
    IN course_id INTEGER,
    IN teacher_id INTEGER,
    IN year INTEGER,
    IN semester INTEGER,
    IN commit_hours INTEGER)
BEGIN
    INSERT INTO teach (course_id, teacher_id, year, semester, commit_hours)
    VALUES (course_id, teacher_id, year, semester, commit_hours);
END //
DELIMITER ;