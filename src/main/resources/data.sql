drop table if exists TypologySystem;
drop table if exists Entry;
drop table if exists Typing;
drop table if exists Enneagram;
drop table if exists Socionics;
drop table if exists AttitudinalPsyche;
drop table if exists MBTI;
drop table if exists OPS;
drop table if exists Teacher;






--create main tables

create table TypologySystem
(id int not null primary key, 
name varchar(255));

create table Teacher
(id int not null primary key, 
name varchar(255));

create table Entry
(id int not null primary key, 
name varchar(255));


--h2 only: foreign key constraint

create table Typing
(id int not null primary key, 
teacherID int not null,
entryID int not null,
typologySystemID int not null,
foreign key (teacherID) references Teacher(id),
foreign key (entryID) references Entry(id),
foreign key (typologySystemID) references TypologySystem(id));











--create typology system tables

--h2 only: foreign key constraint

create table Enneagram
(id int not null primary key, 
teacherID int not null,
core_type int not null check(core_type between 1 and 9),
tritype int,
foreign key (teacherID) references Teacher(id));

create table Socionics
(id int not null primary key);

create table AttitudinalPsyche
(id int not null primary key);

create table MBTI
(id int not null primary key);

create table OPS
(id int not null primary key);








--insert test data into tables

insert into TypologySystem(id, name)
values
(1, 'enneagram'),
(2, 'socionics'),
(3, 'attitudinal_psyche'),
(4, 'mbti'),
(5, 'ops');


insert into Teacher(id, name)
values
(1, 'Rob Zeke'),
(2, 'Enneagrammer'),
(3, 'Goblins of Discord'),
(4, 'Katherine Fauvre'),
(5, 'ops');