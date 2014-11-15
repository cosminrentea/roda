-- user_group
insert into user_group(groupname,enabled,description) values ('ROLE_ADMIN',true,'group for administrators');
insert into user_group(groupname,enabled,description) values ('ROLE_USER',true,'group for users');
insert into user_group(groupname,enabled,description) values ('ROLE_VISITOR',true,'group for visitors');

-- global settings, profile-independent
insert into setting(name, "value") values ('defaultlanguage','en');
insert into setting(name, "value") values ('databrowser_en_url','/en/data-catalog');
insert into setting(name, "value") values ('databrowser_ro_url','/ro/catalogul-de-date');
