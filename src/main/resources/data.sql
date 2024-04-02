--insert test data into tables

insert into Typology_System(id, name)
values
(1, 'enneagram'),
(2, 'socionics'),
(3, 'attitudinal_psyche'),
(4, 'mbti'),
(5, 'ops');


insert into Typist(id, name)
values
(1, 'Admin'),
(2, 'Rob Zeke'),
(3, 'Enneagrammer'),
(4, 'Goblins of Discord'),
(5, 'Katherine Fauvre'),
(6, 'OPS'),
(7, 'Neurotyping');



insert into Enneagram_Typing_Consensus
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
values
(111, 9, 8, 973, 379, 468, 'sp', 'sp/sx', 'synflow', 'UN', 'UN/CY/AY', 'PIS', 'synflow');






insert into Entry(id, name, category, enneagram_typing_consensus_id)
values
(666, 'test123', 'PERSON', 111);




insert into Enneagram_Typing
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
values
(888, 666, 2, 6, 5, 648, 468, 739, 'sp', 'sp/sx', 'synflow', 'EX', 'EX/CY/AY', 'PIS', 'synflow');





insert into Typing(id, typist_id, entry_id, typology_system_id, created_timestamp, updated_timestamp)
values
(345, 2, 666, 1, null, null),
(346, 4, 666, 2, null, null),
(347, 2, 666, 3, null, null);




insert into App_User(id, name, pwd, role, registration_timestamp, status)
values
(11111, 'admin', '123abc', 'ADMIN', null, 'enabled'),
(22222, 'noob2', '123abc', 'USER', null, 'enabled');




insert into Authorities(id, user_id, name)
values
(100, 11111, 'VIEWALL');



