USE trrs;
INSERT INTO teacher (id, name, gender, title)
    VALUE (1, 'Alice', 1, 6);
INSERT INTO teacher (id, name, gender, title)
    VALUE (2, 'Bob', 2, 5);

INSERT INTO paper (id, title, source, date, type, level)
    VALUE (1, 'paper1', 'source1', 20190101, 1, 1);
INSERT INTO paper (id, title, source, date, type, level)
    VALUE (2, 'paper2', 'source2', 20190202, 2, 2);

INSERT INTO project (id, name, source, type, funding, start_year, end_year)
    VALUE (1, 'project1', 'source1', 1, 100, 2019, 2020);
INSERT INTO project (id, name, source, type, funding, start_year, end_year)
    VALUE (2, 'project2', 'source2', 2, 200, 2019, 2020);

INSERT INTO course(id, name, hours, property)
    VALUE (1, 'course1', 100, 1);
INSERT INTO course(id, name, hours, property)
    VALUE (2, 'course2', 200, 2);
