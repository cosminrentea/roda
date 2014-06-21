-- users
insert into users(username,password,enabled) values ('admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',true);
insert into users(username,password,enabled) values ('user','04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb',true);
--insert into users(username,password,enabled) values ('visitor','5f14f9e6d80f802a65269804f2552ef9889f2c7ccec5067214e58a1e48e0b3ff',true);
insert into users(username,password,enabled) values ('visitor','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',true);

--insert into users(username,password,enabled,person_id) values ('admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',true,1);
--insert into users(username,password,enabled,person_id) values ('user','04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb',true,2);
--insert into users(username,password,enabled,person_id) values ('visitor','5f14f9e6d80f802a65269804f2552ef9889f2c7ccec5067214e58a1e48e0b3ff',true,3);

-- user_group
insert into user_group(groupname,enabled,description) values ('ROLE_ADMIN',true,'group for administrators');
insert into user_group(groupname,enabled,description) values ('ROLE_USER',true,'group for users');
insert into user_group(groupname,enabled,description) values ('ROLE_VISITOR',true,'group for visitors');


-- authorities
insert into authorities(username,authority) values ('admin','ROLE_ADMIN');
insert into authorities(username,authority) values ('admin','ROLE_USER');
insert into authorities(username,authority) values ('admin','ROLE_VISITOR');
insert into authorities(username,authority) values ('user','ROLE_USER');
insert into authorities(username,authority) values ('user','ROLE_VISITOR');
insert into authorities(username,authority) values ('visitor','ROLE_VISITOR');

--user_profile
insert into user_profile(user_id, firstname, middlename, lastname, title, salutation, sex, birthdate, image, city, country, address1, phone) values (1, 'Ionica', 'V.', 'Ion', 'inginer', 'domnul', 'M', '11-10-1980', 'default-user-m', 'Bucuresti', 'Romania', 'bd. Magheru nr. 7', '0765432100');
insert into user_profile(user_id, firstname, middlename, lastname, title, salutation, sex, birthdate, image, city, country, address1, phone) values (2, 'Vasile', 'V.', 'Vasilica', 'doctor', 'domnul', 'M', '12-07-1970', 'default-user-m', 'Ploiesti', 'Romania', 'str. Lalelelor nr. 25', '0745678907');
insert into user_profile(user_id, firstname, middlename, lastname, title, salutation, sex, birthdate, image, city, country, address1, phone) values (3, 'Ion', 'T.', 'Popescu', 'doctor', 'domnul', 'M', '03-11-1975', 'default-user-m', 'Bucuresti', 'Romania', 'str. Traian nr. 48', '0729789678');


