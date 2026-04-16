create database BOOKdb;
use BOOKdb;
create table tbl_member (
	  Member_Id INT,
      Member_Name VARCHAR(45) NULL,
      Member_identity VARCHAR(45) NULL,
      Member_grade VARCHAR(45),
      Member_addr VARCHAR(45),
      Member_phone VARCHAR(45)
);

create table tbl_rental (
	Rental_id INT,
    Book_code INT,
    Member_id INT
);

create table tbl_book(
	book_code INT,
    Classification_id INT,
    book_author VARCHAR(45),
    book_name VARCHAR(45),
    publisher VARCHAR(45),
    isrental CHAR(1)
);

alter table tbl_member add constraint PK_member_id primary key(member_id);
alter table tbl_book add constraint PK_bookcode_id primary key(book_code);
alter table tbl_rental add constraint FK_Bookcode 
foreign key(book_code) references tbl_book(book_code)
on delete cascade;
alter table tbl_rental add constraint FK_member_id 
foreign key(member_id) references tbl_member(member_id)
on delete cascade;

insert into tbl_member values
	(111,'aaa','111','일반','대구','010-111-2222'),
    (222,'bbb','222','VIP','울산','010-111-2222'),
    (333,'ccc','333','VIP','인천','010-111-2222'),
    (444,'ddd','444','일반','부산','010-111-2222'),
    (555,'eee','555','VIP','서울','010-111-2222'),
    (666,'fff','666','일반','경기','010-111-2222');
    
insert into tbl_book values
	(1010,'1','윤성우','열혈C','오렌지미디어','1'),
	(1011,'1','남궁성','JAVA의정석','00미디어','1'),
	(1012,'1','남길동','이것이리눅스다','한빛미디어','1'),
	(2010,'2','데일카네기','인간관계론','00미디어','1'),
	(2011,'2','홍길동','미움받을용기','00미디어','1');

insert into tbl_rental values
	(1,1010,111),
	(2,1011,222),
	(3,1012,333);
    
show create table tbl_member;

SELECT * FROM information_schema.table_constraints
WHERE table_schema = 'bookdb';

desc tbl_member;
desc tbl_book;
desc tbl_rental;

create index book_code_idx on tbl_rental(book_code);
create index member_id_idx on tbl_rental(member_id);
show index from tbl_rental;

create view ShowRental_view
as
select rental_id,member_name,book_name
from tbl_rental R
inner join tbl_member M
on R.member_id = M.member_id
inner join tbl_book B
on R.book_code = B.book_code;

select * from showrental_view;


