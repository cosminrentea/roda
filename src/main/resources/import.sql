-- user_group
insert into user_group(groupname,enabled,description) values ('ROLE_ADMIN',true,'group for administrators');
insert into user_group(groupname,enabled,description) values ('ROLE_USER',true,'group for users');
insert into user_group(groupname,enabled,description) values ('ROLE_VISITOR',true,'group for visitors');

-- global settings, profile-independent
insert into setting(name, "value") values ('defaultlanguage','en');
insert into setting(name, "value") values ('en_databrowser_url','en/data-catalog');
insert into setting(name, "value") values ('ro_databrowser_url','ro/catalogul-de-date');
