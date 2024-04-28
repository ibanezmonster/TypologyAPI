use typology_db;




--drop table if exists App_User cascade;
--drop table if exists Attitudinal_Psyche;
--drop table if exists Typing cascade;
--drop table if exists Typology_System cascade;
--drop table if exists Enneagram_Typing cascade;
--drop table if exists Socionics cascade;
--drop table if exists MBTI cascade;
--drop table if exists OPS cascade;
--drop table if exists Authorities cascade;


--drop table if exists Teacher cascade;




--drop table if exists Entry cascade;


----no fk
--drop table if exists Typist cascade;
--drop table if exists Enneagram_Typing_Consensus cascade;
--drop table if exists Test_Mappings cascade;














----create main tables


--create table App_User
--(id int not null auto_increment primary key,
--name varchar(50) not null unique,
--pwd varchar(150) not null,
--role varchar(50) not null,
--registration_timestamp timestamp with time zone,
--status varchar(50) not null);

--create table Authorities
--(id int not null auto_increment primary key,
--user_id int not null,
--name varchar(50) not null,
--foreign key (user_id) references App_User(id)
--);

--create table Typology_System
--(id int not null auto_increment primary key, 
--name varchar(255) not null unique);

--create table Typist
--(id int not null auto_increment primary key, 
--name varchar(255) not null);











--create table Enneagram_Typing_Consensus
--(id int not null auto_increment primary key, 
--core_type int not null check(core_type between 1 and 9),
--wing int not null check(core_type between 1 and 9),
--tritype_ordered int,
--tritype_unordered int,
--overlay int,
--instinct_main varchar(2),
--instinct_stack varchar(8),
--instinct_stack_flow varchar(30),
--ex_instinct_main varchar(2),
--ex_instinct_stack varchar(8),
--ex_instinct_stack_abbreviation varchar(3),
--ex_instinct_stack_flow varchar(30)
--);







--create table Entry
--(id int not null auto_increment primary key,
--name varchar(255) not null,
--category varchar(255),
--enneagram_typing_consensus_id int unique,
--foreign key (enneagram_typing_consensus_id) references Enneagram_Typing_Consensus(id)
--);




--create table Test_Mappings
--(id int not null auto_increment primary key,
--entry_id int not null,
--foreign key (entry_id) references Entry(id));








--create table Enneagram_Typing
--(id int not null auto_increment primary key, 
--entry_id int not null,
--typist_id int not null,
--core_type int not null check(core_type between 1 and 9),
--wing int not null check(core_type between 1 and 9),
--tritype_ordered int,
--tritype_unordered int,
--overlay int,
--instinct_main varchar(2),
--instinct_stack varchar(8),
--instinct_stack_flow varchar(30),
--ex_instinct_main varchar(2),
--ex_instinct_stack varchar(8),
--ex_instinct_stack_abbreviation varchar(3),
--ex_instinct_stack_flow varchar(30),
--foreign key (entry_id) references Entry(id),
--foreign key (typist_id) references Typist(id)
--);








--create table Typing
--(id int not null auto_increment primary key,
--typist_id int not null,
--entry_id int not null,
--typology_system_id int not null,
--created_timestamp timestamp with time zone,
--updated_timestamp timestamp with time zone,
--foreign key (typist_id) references Typist(id),
--foreign key (entry_id) references Entry(id),
--foreign key (typology_system_id) references Typology_System(id)
--);











----create typology system tables

--create table Socionics
--(id int not null auto_increment primary key);

--create table Attitudinal_Psyche
--(id int not null auto_increment primary key);

--create table MBTI
--(id int not null auto_increment primary key);

--create table OPS
--(id int not null auto_increment primary key);



----insert test data into tables

--insert into Typology_System(id, name)
--values
--(1, 'enneagram'),
--(2, 'socionics'),
--(3, 'attitudinal_psyche'),
--(4, 'mbti'),
--(5, 'ops');


--insert into Typist(id, name)
--values
--(1, 'Admin'),
--(2, 'Rob Zeke'),
--(3, 'Enneagrammer'),
--(4, 'Goblins of Discord'),
--(5, 'Katherine Fauvre'),
--(6, 'OPS'),
--(7, 'Neurotyping');



--insert into Enneagram_Typing_Consensus
--(id, 
--core_type, 
--wing, 
--tritype_ordered, 
--tritype_unordered, 
--overlay, 
--instinct_main, 
--instinct_stack, 
--instinct_stack_flow, 
--ex_instinct_main, 
--ex_instinct_stack, 
--ex_instinct_stack_abbreviation, 
--ex_instinct_stack_flow)
--values
--(111, 9, 8, 973, 379, 468, 'sp', 'sp/sx', 'synflow', 'UN', 'UN/CY/AY', 'PIS', 'synflow');






--insert into Entry(id, name, category, enneagram_typing_consensus_id)
--values
--(666, 'test123', 'PERSON', 111);




--insert into Enneagram_Typing
--(id, 
--entry_id,
--typist_id,
--core_type, 
--wing, 
--tritype_ordered, 
--tritype_unordered, 
--overlay, 
--instinct_main, 
--instinct_stack, 
--instinct_stack_flow, 
--ex_instinct_main, 
--ex_instinct_stack, 
--ex_instinct_stack_abbreviation, 
--ex_instinct_stack_flow)
--values
--(888, 666, 2, 6, 5, 648, 468, 739, 'sp', 'sp/sx', 'synflow', 'EX', 'EX/CY/AY', 'PIS', 'synflow');





--insert into Typing(id, typist_id, entry_id, typology_system_id, created_timestamp, updated_timestamp)
--values
--(345, 2, 666, 1, null, null),
--(346, 4, 666, 2, null, null),
--(347, 2, 666, 3, null, null);




--insert into App_User(id, name, pwd, role, registration_timestamp, status)
--values
--(11111, 'admin', '$2a$12$tUBnvhyVdrNdXs3VgTgsgeVohbm0dRF69XoXn2hn7o3eI0gDSg2tC', 'ADMIN', null, 'enabled'),
--(22222, 'noob2', '123abc', 'USER', null, 'enabled');




--insert into Authorities(id, user_id, name)
--values
--(100, 11111, 'VIEWALL'),
--(101, 11111, 'ROLE_ADMIN');



































use typology_db;



--entry, testmappings, enneagram_typing, typing
--IF OBJECT_ID('*Authorities*', 'U') IS NOT NULL 
--ALTER TABLE Authorities
--with check
--ADD CONSTRAINT FK_Authorities_Cascade
--FOREIGN KEY (user_id) 
--REFERENCES App_User(id) 
--ON DELETE CASCADE;

--IF OBJECT_ID('*Entry*', 'U') IS NOT NULL 
--ALTER TABLE Entry
--with check
--ADD CONSTRAINT FK_Entry_Cascade
--FOREIGN KEY (enneagram_typing_consensus_id) REFERENCES Enneagram_Typing_Consensus(id)
--ON DELETE CASCADE;

----FOREIGN KEY (enneagram_typing_consensus_id) REFERENCES Enneagram_Typing_Consensus(id)

--IF OBJECT_ID('*Enneagram_Typing*', 'U') IS NOT NULL 
--ALTER TABLE Enneagram_Typing
--with check
--ADD CONSTRAINT FK_Authorities_Cascade
--FOREIGN KEY (entry_id) REFERENCES Entry(id),
--FOREIGN KEY (typist_id) REFERENCES Typist(id)
--ON DELETE CASCADE;

----FOREIGN KEY (entry_id) REFERENCES Entry(id),
----FOREIGN KEY (typist_id) REFERENCES Typist(id)

--IF OBJECT_ID('*Typing*', 'U') IS NOT NULL 
--ALTER TABLE Typing
--with check
--ADD CONSTRAINT FK_Typing_Cascade 
--FOREIGN KEY (typist_id) REFERENCES Typist(id),
--FOREIGN KEY (entry_id) REFERENCES Entry(id),
--FOREIGN KEY (typology_system_id) REFERENCES Typology_System(id)
--ON DELETE CASCADE;


    --FOREIGN KEY (typist_id) REFERENCES Typist(id),
    --FOREIGN KEY (entry_id) REFERENCES Entry(id),
    --FOREIGN KEY (typology_system_id) REFERENCES Typology_System(id)






----view tables
--select * from app_user;
--select * from typing;
--select * from authorities;
--select * from enneagram_typing;
--select * from entry;
--select * from enneagram_typing_consensus;
--select * from typist;
--select * from typology_system;


--if it doesn't work first time, just rerun


IF OBJECT_ID('Authorities', 'U') IS NOT NULL
    DROP TABLE Authorities;
IF OBJECT_ID('Test_Mappings', 'U') IS NOT NULL
    DROP TABLE Test_Mappings;
IF OBJECT_ID('Enneagram_Typing', 'U') IS NOT NULL
    DROP TABLE Enneagram_Typing;
IF OBJECT_ID('Typing', 'U') IS NOT NULL
    DROP TABLE Typing;
IF OBJECT_ID('Entry', 'U') IS NOT NULL
    DROP TABLE Entry;
IF OBJECT_ID('Enneagram_Typing_Consensus', 'U') IS NOT NULL
    DROP TABLE Enneagram_Typing_Consensus;
IF OBJECT_ID('App_User', 'U') IS NOT NULL
    DROP TABLE App_User;
IF OBJECT_ID('Attitudinal_Psyche', 'U') IS NOT NULL
    DROP TABLE Attitudinal_Psyche;
IF OBJECT_ID('Typology_System', 'U') IS NOT NULL
    DROP TABLE Typology_System;
IF OBJECT_ID('Socionics', 'U') IS NOT NULL
    DROP TABLE Socionics;
IF OBJECT_ID('MBTI', 'U') IS NOT NULL
    DROP TABLE MBTI;
IF OBJECT_ID('OPS', 'U') IS NOT NULL
    DROP TABLE OPS;
IF OBJECT_ID('Teacher', 'U') IS NOT NULL
    DROP TABLE Teacher;
IF OBJECT_ID('Typist', 'U') IS NOT NULL
    DROP TABLE Typist;



CREATE TABLE App_User (
    id INT NOT NULL PRIMARY KEY IDENTITY,
    name VARCHAR(50) NOT NULL UNIQUE,
    pwd VARCHAR(150) NOT NULL,
    role VARCHAR(50) NOT NULL,
    registration_timestamp DATETIMEOFFSET,
    status VARCHAR(50) NOT NULL
);

CREATE TABLE Authorities (
    id INT NOT NULL PRIMARY KEY IDENTITY,
    user_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES App_User(id) on delete cascade
);

CREATE TABLE Typology_System (
    id INT NOT NULL PRIMARY KEY IDENTITY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE Typist (
    id INT NOT NULL PRIMARY KEY IDENTITY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE Enneagram_Typing_Consensus (
    id INT NOT NULL PRIMARY KEY IDENTITY,
    core_type INT NOT NULL CHECK(core_type BETWEEN 1 AND 9),
    wing INT NOT NULL CHECK(wing BETWEEN 1 AND 9),
    tritype_ordered INT,
    tritype_unordered INT,
    overlay INT,
    instinct_main VARCHAR(2),
    instinct_stack VARCHAR(8),
    instinct_stack_flow VARCHAR(30),
    ex_instinct_main VARCHAR(2),
    ex_instinct_stack VARCHAR(8),
    ex_instinct_stack_abbreviation VARCHAR(3),
    ex_instinct_stack_flow VARCHAR(30)
);

CREATE TABLE Entry (
    id INT NOT NULL PRIMARY KEY IDENTITY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255),
    enneagram_typing_consensus_id INT UNIQUE,
    FOREIGN KEY (enneagram_typing_consensus_id) REFERENCES Enneagram_Typing_Consensus(id) on delete cascade
);

CREATE TABLE Test_Mappings (
    id INT NOT NULL PRIMARY KEY IDENTITY,
    entry_id INT NOT NULL,
    FOREIGN KEY (entry_id) REFERENCES Entry(id)
);

CREATE TABLE Enneagram_Typing (
    id INT NOT NULL PRIMARY KEY IDENTITY,
    entry_id INT NOT NULL,
    typist_id INT NOT NULL,
    core_type INT NOT NULL CHECK(core_type BETWEEN 1 AND 9),
    wing INT NOT NULL CHECK(wing BETWEEN 1 AND 9),
    tritype_ordered INT,
    tritype_unordered INT,
    overlay INT,
    instinct_main VARCHAR(2),
    instinct_stack VARCHAR(8),
    instinct_stack_flow VARCHAR(30),
    ex_instinct_main VARCHAR(2),
    ex_instinct_stack VARCHAR(8),
    ex_instinct_stack_abbreviation VARCHAR(3),
    ex_instinct_stack_flow VARCHAR(30),
    FOREIGN KEY (entry_id) REFERENCES Entry(id) on delete cascade,
    FOREIGN KEY (typist_id) REFERENCES Typist(id) on delete cascade
);

CREATE TABLE Typing (
    id INT NOT NULL PRIMARY KEY IDENTITY,
    typist_id INT NOT NULL,
    entry_id INT NOT NULL,
    typology_system_id INT NOT NULL,
    created_timestamp DATETIMEOFFSET,
    updated_timestamp DATETIMEOFFSET,
    FOREIGN KEY (typist_id) REFERENCES Typist(id),
    FOREIGN KEY (entry_id) REFERENCES Entry(id),
    FOREIGN KEY (typology_system_id) REFERENCES Typology_System(id) on delete cascade
);


CREATE TABLE Socionics (
    id INT NOT NULL IDENTITY(1,1) PRIMARY KEY
);

CREATE TABLE Attitudinal_Psyche (
    id INT NOT NULL IDENTITY(1,1) PRIMARY KEY
);

CREATE TABLE MBTI (
    id INT NOT NULL IDENTITY(1,1) PRIMARY KEY
);

CREATE TABLE OPS (
    id INT NOT NULL IDENTITY(1,1) PRIMARY KEY
);



--set identity insert on to allow identity values to be added

--once you set one ON, you can't set anything else ON
--ChatGPT says
--IDENTITY_INSERT is a property in SQL Server that allows explicit values to be inserted into an identity column. 
--However, it can only be enabled for one table at a time within a session.







-- Insert into Typology_System
--IF OBJECT_ID('*Typology_System*', 'U') IS NOT NULL 
SET IDENTITY_INSERT Typology_System ON;
INSERT INTO Typology_System (id, name)
VALUES 
(1, 'enneagram'),
(2, 'socionics'),
(3, 'attitudinal_psyche'),
(4, 'mbti'),
(5, 'ops');


-- Insert into Typist
--IF OBJECT_ID('*Typist*', 'U') IS NOT NULL 
SET IDENTITY_INSERT Typology_System OFF;
SET IDENTITY_INSERT Typist ON;
INSERT INTO Typist (id, name)
VALUES
(1, 'Admin'),
(2, 'Rob Zeke'),
(3, 'Enneagrammer'),
(4, 'Goblins of Discord'),
(5, 'Katherine Fauvre'),
(6, 'OPS'),
(7, 'Neurotyping');


-- Insert into Enneagram_Typing_Consensus
--IF OBJECT_ID('*Enneagram_Typing_Consensus*', 'U') IS NOT NULL 
SET IDENTITY_INSERT Typist OFF;
SET IDENTITY_INSERT Enneagram_Typing_Consensus ON;
INSERT INTO Enneagram_Typing_Consensus
(id, 
core_type, 
wing, 
tritype_ordered, 
tritype_unordered, 
overlay, 
instinct_main, 
instinct_stack, 
instinct_stack_flow, 
ex_instinct_main, 
ex_instinct_stack, 
ex_instinct_stack_abbreviation, 
ex_instinct_stack_flow)
VALUES
(111, 9, 8, 973, 379, 468, 'sp', 'sp/sx', 'synflow', 'UN', 'UN/CY/AY', 'PIS', 'synflow');


-- Insert into Entry
--IF OBJECT_ID('*Entry*', 'U') IS NOT NULL 
SET IDENTITY_INSERT Enneagram_Typing_Consensus OFF;
SET IDENTITY_INSERT Entry ON;
INSERT INTO Entry(id, name, category, enneagram_typing_consensus_id)
VALUES
(666, 'test123', 'PERSON', 111);



-- Insert into Enneagram_Typing
--IF OBJECT_ID('*Enneagram_Typing*', 'U') IS NOT NULL 
SET IDENTITY_INSERT Entry OFF;
SET IDENTITY_INSERT Enneagram_Typing ON;
INSERT INTO Enneagram_Typing
(id, 
entry_id,
typist_id,
core_type, 
wing, 
tritype_ordered, 
tritype_unordered, 
overlay, 
instinct_main, 
instinct_stack, 
instinct_stack_flow, 
ex_instinct_main, 
ex_instinct_stack, 
ex_instinct_stack_abbreviation, 
ex_instinct_stack_flow)
VALUES
(888, 666, 2, 6, 5, 648, 468, 739, 'sp', 'sp/sx', 'synflow', 'EX', 'EX/CY/AY', 'PIS', 'synflow');



-- Insert into Typing
--IF OBJECT_ID('*Typing*', 'U') IS NOT NULL 
SET IDENTITY_INSERT Enneagram_Typing OFF;
SET IDENTITY_INSERT Typing ON;
INSERT INTO Typing(id, typist_id, entry_id, typology_system_id, created_timestamp, updated_timestamp)
VALUES
(345, 2, 666, 1, null, null),
(346, 4, 666, 2, null, null),
(347, 2, 666, 3, null, null);



-- Insert into App_User
--IF OBJECT_ID('*App_User*', 'U') IS NOT NULL 
SET IDENTITY_INSERT Typing OFF;
SET IDENTITY_INSERT App_User ON;
INSERT INTO App_User(id, name, pwd, role, registration_timestamp, status)
VALUES
(11111, 'admin', '$2a$12$tUBnvhyVdrNdXs3VgTgsgeVohbm0dRF69XoXn2hn7o3eI0gDSg2tC', 'ADMIN', null, 'enabled'),
(22222, 'noob2', '123abc', 'USER', null, 'enabled');



-- Insert into Authorities
--IF OBJECT_ID('*Authorities*', 'U') IS NOT NULL 
SET IDENTITY_INSERT App_User OFF;
SET IDENTITY_INSERT Authorities ON;
INSERT INTO Authorities(id, user_id, name)
VALUES
(100, 11111, 'VIEWALL'),
(101, 11111, 'ROLE_ADMIN');
