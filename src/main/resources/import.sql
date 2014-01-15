-- users
insert into users(username,password,enabled) values ('admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',true);
insert into users(username,password,enabled) values ('user','04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb',true);
insert into users(username,password,enabled) values ('visitor','5f14f9e6d80f802a65269804f2552ef9889f2c7ccec5067214e58a1e48e0b3ff',true);
--insert into users(username,password,enabled,person_id) values ('admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',true,1);
--insert into users(username,password,enabled,person_id) values ('user','04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb',true,2);
--insert into users(username,password,enabled,person_id) values ('visitor','5f14f9e6d80f802a65269804f2552ef9889f2c7ccec5067214e58a1e48e0b3ff',true,3);

-- authorities
insert into authorities(username,authority) values ('admin','ROLE_ADMIN');
insert into authorities(username,authority) values ('admin','ROLE_USER');
insert into authorities(username,authority) values ('admin','ROLE_VISITOR');
insert into authorities(username,authority) values ('user','ROLE_USER');
insert into authorities(username,authority) values ('user','ROLE_VISITOR');
insert into authorities(username,authority) values ('visitor','ROLE_VISITOR');

-- user_groups
--insert into user_groups(group_name,enabled,description) values ('ROLE_ADMIN',true,'group for administrators');
--insert into user_groups(group_name,enabled,description) values ('ROLE_USER',true,'group for users');
--insert into user_groups(group_name,enabled,description) values ('ROLE_VISITOR',true,'group for visitors');
