create table Book
(
    id            char(8) primary key,
    name          varchar(10) not null,
    author        varchar(10),
    price         float,
    status        int check ( status in (0, 1, 2) ) default 0,
    borrow_times  int,
    reserve_times int                               default 0
);

create table Reader
(
    id      char(8) primary key,
    name    varchar(10),
    age     int,
    address varchar(20)
);

create table Borrow
(
    book_id     char(8) references Book (id),
    reader_id   char(8) references Reader (id),
    borrow_date date,
    return_date date,
    primary key (book_id, reader_id, borrow_date)
);

create table Reserve
(
    book_id      char(8) references Book (id),
    reader_id    char(8) references Reader (id),
    reserve_date date default (curdate()),
    take_date    date,
    primary key (book_id, reader_id, reserve_date),
    check ( take_date >= reserve_date )
);

