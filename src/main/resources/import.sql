-- user_group
insert into user_group(groupname,enabled,description) values ('ROLE_ADMIN',true,'group for administrators');
insert into user_group(groupname,enabled,description) values ('ROLE_USER',true,'group for users');
insert into user_group(groupname,enabled,description) values ('ROLE_VISITOR',true,'group for visitors');


-- languages
INSERT INTO lang VALUES (1, 'ro', NULL, NULL, 'română');
INSERT INTO lang VALUES (2, 'en', NULL, NULL, 'english');
INSERT INTO lang VALUES (3, 'fr', NULL, NULL, 'français');
INSERT INTO lang VALUES (4, 'fi', NULL, NULL, 'suomi');
INSERT INTO lang VALUES (5, 'de', NULL, NULL, 'deutsch');
INSERT INTO lang VALUES (6, 'he', NULL, NULL, 'עברית');
INSERT INTO lang VALUES (7, 'hu', NULL, NULL, 'magyar');
INSERT INTO lang VALUES (8, 'it', NULL, NULL, 'italiano');
INSERT INTO lang VALUES (9, 'lv', NULL, NULL, 'latviešu');
INSERT INTO lang VALUES (10, 'pl', NULL, NULL, 'polszczyzna');
INSERT INTO lang VALUES (11, 'pt', NULL, NULL, 'português');
INSERT INTO lang VALUES (12, 'ru', NULL, NULL, 'русский');
INSERT INTO lang VALUES (13, 'sk', NULL, NULL, 'slovenčina');

-- Name: lang_id_seq; Type: SEQUENCE SET; Schema: public; Owner: roda

SELECT pg_catalog.setval('lang_id_seq', 13, true);


-- global settings, profile-independent
insert into setting(name, "value") values ('defaultlanguage','en');
insert into setting(name, "value") values ('en_databrowser_url','en/data-catalog');
insert into setting(name, "value") values ('ro_databrowser_url','ro/catalogul-de-date');
