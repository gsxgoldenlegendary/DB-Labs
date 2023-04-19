use lab1;
#3
DELIMITER //
DROP PROCEDURE IF EXISTS updateReaderID;
CREATE PROCEDURE updateReaderID(IN old_ID CHAR(8), IN new_ID CHAR(8))
BEGIN
    DECLARE S int DEFAULT 0;
    SELECT COUNT(*) INTO S FROM Reader WHERE ID = new_ID;
    IF S > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'new ID already exists';
    end if;
    SELECT COUNT(*) INTO S FROM Reader WHERE ID = old_ID;
    IF S = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'old ID does not exist';
    end if;
    start transaction ;
    set foreign_key_checks = 0;
    UPDATE Borrow SET reader_ID = new_ID WHERE reader_ID = old_ID;
    UPDATE Reserve SET reader_ID = new_ID WHERE reader_ID = old_ID;
    UPDATE Reader SET ID = new_ID WHERE ID = old_ID;
    set foreign_key_checks = 1;
    commit;
END //
DELIMITER ;

call updateReaderID('r1', 'r2');
call updateReaderID('test', 'r100');
call updateReaderID('r1', 'r24');


#4
DELIMITER //
DROP PROCEDURE IF EXISTS borrowBook;
CREATE PROCEDURE borrowBook(IN r_ID CHAR(8), IN b_ID CHAR(8))
BEGIN
    DECLARE b_date DATE;
    DECLARE other_reserve_exists INT;
    DECLARE reserve_exists INT;
    DECLARE current_borrow_count INT;
    DECLARE reader_exists INT;
    DECLARE have_borrowed INT;
    DECLARE book_not_exists INT;

    SET b_date = CURDATE();

    SELECT COUNT(*) INTO book_not_exists FROM Book WHERE ID = b_ID;
    IF book_not_exists = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'book does not exist';
    END IF;

    SELECT COUNT(*) INTO reader_exists FROM Reader WHERE ID = r_ID;
    IF reader_exists = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'reader does not exist';
    END IF;

    SELECT COUNT(*)
    INTO have_borrowed
    FROM Borrow
    WHERE book_ID = b_ID
      AND reader_ID = r_ID
      AND borrow_date = b_date;
    IF have_borrowed THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'reader has borrowed this book today';
    END IF;

    SELECT COUNT(*)
    INTO other_reserve_exists
    FROM Reserve
    WHERE book_ID = b_ID
      AND reader_ID != r_ID;
    SELECT COUNT(*)
    INTO reserve_exists
    FROM Reserve
    WHERE book_ID = b_ID
      AND reader_ID = r_ID;

    IF other_reserve_exists != 0 and reserve_exists = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'book is not reserved by this reader';
    END IF;

    SELECT COUNT(*)
    INTO current_borrow_count
    FROM Borrow
    WHERE reader_ID = r_ID
      AND return_date IS NULL;
    IF current_borrow_count >= 3 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'reader has borrowed 3 books';
    END IF;

    INSERT INTO Borrow (book_ID, reader_ID, borrow_date)
                VALUES (b_ID, r_ID, b_date);
                DELETE
                FROM Reserve
                WHERE book_ID = b_ID
                  AND reader_ID = r_ID;
                UPDATE Book
                SET borrow_times = borrow_times + 1,
                    status       = 1
                WHERE ID = b_ID;

END //
DELIMITER ;
call borrowBook('r1', 'b2');
#5
DELIMITER //
DROP PROCEDURE IF EXISTS returnBook;
CREATE PROCEDURE returnBook(IN r_ID CHAR(8), IN b_ID CHAR(8))
BEGIN
    DECLARE r_date DATE;
    DECLARE reader_exists INT;
    DECLARE book_exists INT;
    DECLARE not_borrowed INT;

    SELECT COUNT(*) INTO reader_exists FROM Reader WHERE ID = r_ID;
    IF reader_exists = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'reader does not exist';
    END IF;

    SELECT COUNT(*) INTO book_exists FROM Book WHERE ID = b_ID;
    IF book_exists = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'book does not exist';
    END IF;

    SELECT COUNT(*) INTO not_borrowed FROM Borrow WHERE book_ID = b_ID AND reader_ID = r_ID AND return_date IS NULL;
    IF not_borrowed = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'book is not borrowed by this reader';
    END IF;

    SET r_date = CURDATE();


    UPDATE Borrow
    SET return_date = r_date
    WHERE book_ID = b_ID
      AND reader_ID = r_ID
      AND return_date IS NULL;

    UPDATE Book
    SET status = 2
    WHERE ID = b_ID
      AND EXISTS (SELECT *
                      FROM Reserve
                      WHERE book_ID = b_ID);
    UPDATE Book
    SET status = 0
    WHERE ID = b_ID
      AND NOT EXISTS (SELECT *
                      FROM Reserve
                      WHERE book_ID = b_ID);
END //
DELIMITER ;
call returnBook('r1', 'b2');
#6
DELIMITER //
CREATE TRIGGER reserve_book_trigger
    AFTER INSERT
    ON Reserve
    FOR EACH ROW
BEGIN
    UPDATE Book SET status = 2, reserve_times = reserve_times + 1 WHERE ID = NEW.book_ID;
END //
DELIMITER ;

DELIMITER //
CREATE TRIGGER cancel_reserve_trigger
    AFTER DELETE
    ON Reserve
    FOR EACH ROW
BEGIN
    UPDATE Book SET reserve_times = reserve_times - 1 WHERE ID = OLD.book_ID;
    IF NOT EXISTS (SELECT * FROM Reserve WHERE book_ID = OLD.book_ID) THEN
        UPDATE Book SET status = 0 WHERE ID = OLD.book_ID;
    END IF;
END //
DELIMITER ;

# DELIMITER //
# CREATE TRIGGER borrow_book_trigger
#     AFTER INSERT
#     ON Borrow
#     FOR EACH ROW
# BEGIN
#     UPDATE Book SET status = 1, borrow_times = borrow_times + 1 WHERE ID = NEW.book_ID;
# END //

INSERT INTO Reserve (book_ID, reader_ID)
VALUES ('b2', 'r1');

INSERT INTO Reserve (book_ID, reader_ID, reserve_date)
VALUES ('b2', 'r23', '2019-01-01');