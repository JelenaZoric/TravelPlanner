insert into location (id, longitude, latitude, city, country) values (1001, 12.496365, 41.902782, 'Rome', 'Italy'),
  (1002, 19.833549, 45.267136, 'Novi Sad', 'Serbia'),
  (1003, 14.268120, 40.851799, 'Naples', 'Italy');
-- --
insert into object (id,name,location_id,type,rating) values (1, 'Giuseppe Meazza', 1001, 1,4.5);
insert into object (id,name,location_id,type,rating) values (2, 'Hotel Park', 1002, 0, 3.2);
insert into object (id,name,location_id,type,rating) values (3, 'Colosseum', 1001, 6, 3.5);
insert into object (id,name,location_id,type,rating) values (4, 'Pantheon', 1001, 6, 4.5);
insert into object (id,name,location_id,type,rating) values (5, 'Promenada', 1002, 7, 2.3);
insert into object (id,name,location_id,type,rating) values (6, 'Vesuvius', 1003, 3, 5.0);
