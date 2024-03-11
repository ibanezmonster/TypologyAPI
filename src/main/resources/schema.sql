drop table if exists App_User cascade;
drop table if exists Typology_System;
drop table if exists Attitudinal_Psyche;
drop table if exists Entry;
drop table if exists Typing;
drop table if exists Enneagram_Typing;
drop table if exists Socionics;
drop table if exists MBTI;
drop table if exists OPS;
drop table if exists Teacher;
drop table if exists Authorities;



--create main tables


create table App_User
(id int not null auto_increment primary key,
name varchar(50) not null unique,
pwd varchar(150) not null,
role varchar(50) not null,
registration_timestamp timestamp with time zone,
status varchar(50) not null);

create table Authorities
(id int not null auto_increment primary key,
authority varchar(50) not null);

create table Typology_System
(id int not null primary key, 
name varchar(255));

create table Teacher
(id int not null primary key, 
name varchar(255));

create table Entry
(id int not null primary key, 
name varchar(255),
enneagram_core_type int);


--h2 only: foreign key constraint

create table Typing
(id int not null primary key, 
teacherID int not null,
entryID int not null,
typologySystemID int not null,
foreign key (teacherID) references Teacher(id),
foreign key (entryID) references Entry(id),
foreign key (typologySystemID) references Typology_System(id));











--create typology system tables

--h2 only: foreign key constraint

create table Enneagram_Typing
(id int not null primary key, 
core_type int not null check(core_type between 1 and 9),
wing int not null check(core_type between 1 and 9),
tritype_ordered int,
tritype_unordered int,
overlay int,
instinct_main varchar(2),
instinct_stack varchar(8),
instinct_stack_flow varchar(30),
ex_instinct_main varchar(2),
ex_instinct_stack varchar(8),
ex_instinct_stack_abbreviation varchar(3),
ex_instinct_stack_flow varchar(30)
);

create table Socionics
(id int not null primary key);

create table Attitudinal_Psyche
(id int not null primary key);

create table MBTI
(id int not null primary key);

create table OPS
(id int not null primary key);