use typology_db;




use test_typology_db;

select * from app_user;

--select * from typist;

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