--insert into user (login, password, device_id) values ("chicken", "chicken2", "Galaxy S4");
insert into user (login, password, firstname, lastname, birthdate) values ("user1", "chicken2", "Jan", "Polak", '1989-10-14');
insert into user (login, password, firstname, lastname, birthdate) values ("user2", "chicken2", "Jan", "Polak", '1989-10-14');
insert into user (login, password, firstname, lastname, birthdate) values ("user3", "chicken2", "Jan", "Polak", '1989-10-14');
insert into user (login, password, firstname, lastname, birthdate) values ("user4", "chicken2", "Jan", "Polak", '1989-10-14');
insert into user (login, password, firstname, lastname, birthdate) values ("user5", "chicken2", "Jan", "Polak", '1989-10-14');
insert into hitchhiker (user_id, number_of_passengers, baggage, sex_type, age_type, children, final_destination, geo_latitude, geo_longitude, active, device_id) values (1, 3, 'BIG', 'MIXED', 'YOUNG', false, 'Kraków', 50.503361, 20.514692, true, 'dev1');
insert into hitchhiker (user_id, number_of_passengers, baggage, sex_type, age_type, children, final_destination, geo_latitude, geo_longitude, active, device_id) values (2, 3, 'BIG', 'MIXED', 'YOUNG', false, 'Slomniki', 50.238400, 20.139097, true, 'dev2');
insert into hitchhiker (user_id, number_of_passengers, baggage, sex_type, age_type, children, final_destination, geo_latitude, geo_longitude, active, device_id) values (3, 3, 'BIG', 'MIXED', 'YOUNG', false, 'Zakrz', 50.031464, 19.915031, true, 'dev3');
insert into hitchhiker (user_id, number_of_passengers, baggage, sex_type, age_type, children, final_destination, geo_latitude, geo_longitude, active, device_id) values (4, 3, 'BIG', 'MIXED', 'YOUNG', false, 'AK', 50.080674, 19.936102, true, 'dev4');
insert into hitchhiker (user_id, number_of_passengers, baggage, sex_type, age_type, children, final_destination, geo_latitude, geo_longitude, active, device_id) values (4, 3, 'BIG', 'MIXED', 'YOUNG', false, 'AK_NOT_ACTIVE', 50.080674, 19.936102, false, 'dev5');
insert into hitchhiker (user_id, number_of_passengers, baggage, sex_type, age_type, children, final_destination, geo_latitude, geo_longitude, active, device_id) values (5, 3, 'BIG', 'MIXED', 'YOUNG', false, 'Wawa', 52.225896, 20.917161, true, 'dev6');
insert into driver (user_id, car_type, car_color, device_id, active, number_of_passengers, geo_latitude, geo_longitude) values (1, "Audi A4", "black", "my_device", false, 4, '50.062440', '19.936102');


-- Krk center
--50.062440, 19.936102
--Home < 10km
-- 50.080674, 19.888595
-- Zakrzowek < 10 km
-- 50.031464, 19.915031
-- Slomniki < 50km
-- 50.238400, 20.139097
-- Pinczow < 150
-- 50.503361, 20.514692
--Wawa > 150
-- 52.225896, 20.917161