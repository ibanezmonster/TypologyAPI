drop table if exists App_User cascade;
drop table if exists Attitudinal_Psyche;
drop table if exists Typing cascade;
drop table if exists Typology_System cascade;
drop table if exists Enneagram_Typing cascade;
drop table if exists Socionics cascade;
drop table if exists MBTI cascade;
drop table if exists OPS cascade;
drop table if exists Authorities cascade;


drop table if exists Teacher cascade;




drop table if exists Entry cascade;


--no fk
drop table if exists Typist cascade;
drop table if exists Enneagram_Typing_Consensus cascade;
drop table if exists Test_Mappings cascade;














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
(id int not null auto_increment primary key, 
name varchar(255) not null unique);

create table Typist
(id int not null auto_increment primary key, 
name varchar(255) not null);











create table Enneagram_Typing_Consensus
(id int not null auto_increment primary key, 
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







create table Entry
(id int not null auto_increment primary key,
name varchar(255) not null,
category varchar(255),
enneagram_typing_consensus_id int unique,
foreign key (enneagram_typing_consensus_id) references Enneagram_Typing_Consensus(id)
);




create table Test_Mappings
(id int not null auto_increment primary key,
entry_id int not null,
foreign key (entry_id) references Entry(id));








create table Enneagram_Typing
(id int not null auto_increment primary key, 
entry_id int not null,
typist_id int not null,
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
ex_instinct_stack_flow varchar(30),
foreign key (entry_id) references Entry(id),
foreign key (typist_id) references Typist(id)
);








create table Typing
(id int not null auto_increment primary key,
typist_id int not null,
entry_id int not null,
typology_system_id int not null,
created_timestamp timestamp with time zone,
updated_timestamp timestamp with time zone,
foreign key (typist_id) references Typist(id),
foreign key (entry_id) references Entry(id),
foreign key (typology_system_id) references Typology_System(id)
);











--create typology system tables

create table Socionics
(id int not null auto_increment primary key);

create table Attitudinal_Psyche
(id int not null auto_increment primary key);

create table MBTI
(id int not null auto_increment primary key);

create table OPS
(id int not null auto_increment primary key);



