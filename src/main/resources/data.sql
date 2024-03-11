--insert test data into tables

insert into Typology_System(id, name)
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


insert into Enneagram_Typing
(id, core_type, wing, tritype_ordered, tritype_unordered, overlay, instinct_main, instinct_stack, instinct_stack_flow, ex_instinct_main, ex_instinct_stack, ex_instinct_stack_abbreviation, ex_instinct_stack_flow)
values
(111, 9, 8, 973, 379, 468, 'sp', 'sp/sx', 'synflow', 'UN', 'UN/CY/AY', 'PIS', 'synflow');

insert into Entry(id, name, enneagram_core_type)
values
(1, 'Cupcakke', 9);

insert into App_User(id, name, pwd, role, registration_timestamp, status)
values
(11111, 'admin', '123', 'ADMIN', null, 'enabled'),
(22222, 'noob2', '123abc', 'USER', null, 'enabled');

insert into Authorities(authority)
values
('admin');