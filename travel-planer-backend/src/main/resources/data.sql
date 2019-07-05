insert into location (id, longitude, latitude, city, country) values (1001, 12.496365, 41.902782, 'Rome', 'Italy'),
  (1002, 19.833549, 45.267136, 'Novi Sad', 'Serbia'),
  (1003, 14.268120, 40.851799, 'Naples', 'Italy');
-- --
--insert into object (id,name,location_id,type,rating) values (1, 'Giuseppe Meazza', 1001, 1,4.5);
--insert into object (id,name,location_id,type,rating) values (2, 'Hotel Park', 1002, 0, 3.2);
--insert into object (id,name,location_id,type,rating) values (3, 'Colosseum', 1001, 6, 3.5);
--insert into object (id,name,location_id,type,rating) values (4, 'Pantheon', 1001, 6, 4.5);
--insert into object (id,name,location_id,type,rating) values (5, 'Promenada', 1002, 7, 2.3);
--insert into object (id,name,location_id,type,rating) values (6, 'Vesuvius', 1003, 3, 5.0);
-- insert into object (id, name, location_id, address, email, phone_number, type, rating, image_path, description) values
--   (1, 'Hotel 1', 1001, 'Address 1', 'h1@h.h', '421312321', 0, 3.7, 'Image path 1', 'Description 1');

--insert into transportation (id, departure, location_id) values (1, TO_DATE('2019-07-03 10:53:56', 'SYYYY-MM-DD HH24:MI:SS'), 1001),
--  (2, TO_DATE('2019-07-05 10:53:56', 'SYYYY-MM-DD HH24:MI:SS'), 1002),
--  (3, TO_DATE('2019-07-04 23:23:23', 'SYYYY-MM-DD HH24:MI:SS'), 1003);

--insert into travel (id, origin_id, destination_id, currency, mode, accommodation_id) values (1, 1, 2, 'EUR', 0, 1),
--  (2, 2, 1, 'EUR', 0, 2),
--  (3, 2, 3, 'EUR', 0, 6);
  
--insert into route (id, name, date, route_travel_id) values (1, 'ruta1', TO_DATE('2019-07-03 10:53:56', 'SYYYY-MM-DD HH24:MI:SS'), 1);

insert into user (id, email, password, first_name, last_name, location_id) values (1, 'a@a.a', 'Aaaaaaaa', 'Aaa', 'Aaaa', 1001),
    (2, 'b@b.b', 'Bbbbbbbb', 'Bbb', 'Bbbb', 1002);

--insert into user_travels (user_id, travels_id) values (1, 1),
--  (2, 2);