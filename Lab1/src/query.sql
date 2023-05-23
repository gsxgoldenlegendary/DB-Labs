use lab1;
#1
SELECT Book.ID, Book.name, Borrow.borrow_date
from Book,
     Borrow
where Book.ID = Borrow.book_id
  and Borrow.reader_id = (SELECT Reader.ID
                          from Reader
                          where Reader.name = 'Rose');
#2
SELECT Reader.ID, Reader.name
from Reader
where Reader.ID not in (SELECT Borrow.reader_id from Borrow)
  and Reader.ID not in (SELECT Reserve.reader_id from Reserve);
#3
select author
from (select Book.author as author, sum(Book.borrow_times) as sum_borrow_times
      from Book
      group by Book.author) as asbt
where sum_borrow_times = (select max(sum_borrow_times)
                          from (select sum(Book.borrow_times) as sum_borrow_times
                                from Book
                                group by Book.author) as asbt);
#4
select Book.ID, Book.name
from Book
where exists(select Borrow.return_date
             from Borrow
             where Borrow.book_id = Book.ID
               and Borrow.return_date is null)
  and Book.name like '%MySQL%';
#5
select Reader.name
from Reader,
     (select Borrow.reader_id, count(*)
      from Borrow
#       where Borrow.return_date is not null
      group by Borrow.reader_id
      having count(*) > 10) as ric
where Reader.ID = ric.reader_id;
#6
select Reader.ID, Reader.name
from Reader
where not exists(select *
                 from Borrow
                 where Reader.ID = Borrow.reader_id
                   and exists(select *
                              from Book
                              where Book.ID = Borrow.book_id
                                and Book.author = 'John'));
#7
select Reader.ID, Reader.name, count(*) as sum
from Reader,
     Borrow
where Reader.ID = Borrow.reader_id
  and Borrow.borrow_date LIKE '2022%'
group by Reader.ID
order by count(*) desc
limit 10;
#8
create view borrow_info as
select Reader.ID   as reader_id,
       Reader.name as reader_name,
       Book.ID     as book_id,
       Book.name   as book_name,
       Borrow.borrow_date
from Reader,
     Book,
     Borrow
where Reader.ID = Borrow.reader_id
  and Book.ID = Borrow.book_id;

select reader_id, count(book_id) as kind_of_books
from (select distinct reader_id, book_id
      from borrow_info
      where borrow_date > date_sub(curdate(), interval 1 year)
      group by reader_id, book_id) as b
group by reader_id;