create table Book
(
    id            char(8),
    name          varchar(10) not null,
    author        varchar(10),
    price         float,
    status        int check ( status in (0, 1, 2) ) default 0,
    borrow_times  int,
    reserve_times int                               default 0,
    constraint PK_Book primary key (id)
);

create table Reader
(
    id      char(8),
    name    varchar(10),
    age     int,
    address varchar(20),
    constraint PK_Reader primary key (id)
);

create table Borrow
(
    book_id     char(8) ,
    reader_id   char(8) ,
    borrow_date date,
    return_date date,
    constraint PK_Borrow primary key (book_id, reader_id, borrow_date),
    constraint FK_Borrow_Book foreign key (book_id) references Book (id),
    constraint FK_Borrow_Reader foreign key (reader_id) references Reader (id)
);

create table Reserve
(
    book_id      char(8),
    reader_id    char(8),
    reserve_date date default (curdate()),
    take_date    date,
    constraint PK_Reserve primary key (book_id, reader_id, reserve_date),
    constraint check ( take_date >= reserve_date ),
    constraint FK_Reserve_Book foreign key (book_id) references Book (id),
    constraint FK_Reserve_Reader foreign key (reader_id) references Reader (id)
);
