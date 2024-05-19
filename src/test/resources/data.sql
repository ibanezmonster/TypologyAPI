use test_typology_api_db;

-- Insert into Typology_System
--IF OBJECT_ID('*Typology_System*', 'U') IS NOT NULL 
SET IDENTITY_INSERT Typology_System ON;
INSERT INTO Typology_System (id, name)
VALUES 
--(1, 'enneagram'),
(1, 'socionics'),
(2, 'attitudinal_psyche'),
(3, 'mbti'),
(4, 'ops');


-- Insert into Typist
--IF OBJECT_ID('*Typist*', 'U') IS NOT NULL 
SET IDENTITY_INSERT Typology_System OFF;
SET IDENTITY_INSERT Typist ON;
INSERT INTO Typist (id, name)
VALUES
(1, 'Admin'),
(2, 'Rob Zeke'),
(3, 'EU'),
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
(111, 9, 8, 973, 379, 468, 'sp', 'sp/sx', 'synflow', 'UN', 'UN/CY/SY', 739, 'PIS');


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
(888, 666, 2, 6, 5, 648, 468, 739, 'sp', 'sp/sx', 'synflow', 'EX', 'EX/CY/FD', '638', 'PIS');



-- Insert into Typing
--IF OBJECT_ID('*Typing*', 'U') IS NOT NULL 
SET IDENTITY_INSERT Enneagram_Typing OFF;
--SET IDENTITY_INSERT Typing ON;
INSERT INTO Typing(typist_id, entry_id, typology_system_id, created_timestamp, updated_timestamp)
VALUES
(2, 666, 1, null, null),
(4, 666, 2, null, null),
(2, 666, 3, null, null);



-- Insert into App_User
--IF OBJECT_ID('*App_User*', 'U') IS NOT NULL 
--SET IDENTITY_INSERT Typing OFF;
SET IDENTITY_INSERT App_User ON;
INSERT INTO App_User(id, name, pwd, role, registration_timestamp, status)
VALUES
(11111, 'admin', '$2a$12$tUBnvhyVdrNdXs3VgTgsgeVohbm0dRF69XoXn2hn7o3eI0gDSg2tC', 'ADMIN', null, 'enabled'),
(22222, 'noob33', '123abc', 'USER', null, 'enabled');



-- Insert into Authorities
--IF OBJECT_ID('*Authorities*', 'U') IS NOT NULL 
SET IDENTITY_INSERT App_User OFF;
SET IDENTITY_INSERT Authorities ON;
INSERT INTO Authorities(id, user_id, name)
VALUES
(100, 11111, 'VIEWALL'),
(101, 11111, 'ROLE_ADMIN');
SET IDENTITY_INSERT Authorities OFF;