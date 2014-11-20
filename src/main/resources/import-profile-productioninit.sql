-- users

-- admin password: R0daAd0rAb11
insert into users(username,password,enabled) values ('admin','25bea7864803ca10c48b33a56ae1225d692a57fc1b5739c08b9f43420a0ff156',true);

-- user password: Ab0rdab11
insert into users(username,password,enabled) values ('user','d9a440f9f8fd4b3d8c2a7c0f3540e05435daecb6348b1dd2ba31f46bdd55630d',true);

-- visitor password: V1s1t0r
insert into users(username,password,enabled) values ('visitor','e61bda547b339685a93108103e97684a3081b5047621b2ec3bebe240d15cd8dc',true);

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

-- global settings 
insert into setting(name, "value") values ('baseurl','/');
insert into setting(name, "value") values ('solrqueryurl','http://localhost:8983/solr/');
