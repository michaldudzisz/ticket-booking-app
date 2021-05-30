
INSERT INTO movies (title, description) VALUES ('La Vita E Bella', 'A nice movie.');
INSERT INTO movies (title, description) VALUES ('Gladiator', 'A cool movie.');
INSERT INTO movies (title, description) VALUES ('Ranczo Wilkowyje', 'A funny movie.');

INSERT INTO rooms (id) VALUES (1);
INSERT INTO rooms (id) VALUES (2);
INSERT INTO rooms (id) VALUES (3);

INSERT INTO screenings (room_id, movie_id, date) VALUES (1, 1, parsedatetime('30-06-2021 12:00:00.00', 'dd-MM-yyyy HH:mm:ss.SS'));
INSERT INTO screenings (room_id, movie_id, date) VALUES (1, 2, parsedatetime('30-06-2021 14:15:00.00', 'dd-MM-yyyy HH:mm:ss.SS'));
INSERT INTO screenings (room_id, movie_id, date) VALUES (2, 1, parsedatetime('30-06-2021 09:15:00.00', 'dd-MM-yyyy HH:mm:ss.SS'));
INSERT INTO screenings (room_id, movie_id, date) VALUES (2, 3, parsedatetime('30-06-2021 13:15:00.00', 'dd-MM-yyyy HH:mm:ss.SS'));
INSERT INTO screenings (room_id, movie_id, date) VALUES (3, 2, parsedatetime('30-06-2021 14:15:00.00', 'dd-MM-yyyy HH:mm:ss.SS'));
INSERT INTO screenings (room_id, movie_id, date) VALUES (3, 3, parsedatetime('30-06-2021 18:15:00.00', 'dd-MM-yyyy HH:mm:ss.SS'));

-- room 1
INSERT INTO seat_rows (room_id, row_nr, row_length) VALUES (1, 1, 5);
INSERT INTO seat_rows (room_id, row_nr, row_length) VALUES (1, 2, 5);
-- room 2
INSERT INTO seat_rows (room_id, row_nr, row_length) VALUES (2, 1, 10);
INSERT INTO seat_rows (room_id, row_nr, row_length) VALUES (2, 2, 10);
INSERT INTO seat_rows (room_id, row_nr, row_length) VALUES (2, 3, 10);
INSERT INTO seat_rows (room_id, row_nr, row_length) VALUES (2, 4, 10);
-- room 3
INSERT INTO seat_rows (room_id, row_nr, row_length) VALUES (3, 1, 8);
INSERT INTO seat_rows (room_id, row_nr, row_length) VALUES (3, 2, 8);
INSERT INTO seat_rows (room_id, row_nr, row_length) VALUES (3, 3, 8);


INSERT INTO reservations (screening_id, first_name, last_name, done) VALUES (1, 'Michał', 'Dudzisz', parsedatetime('29-05-2021 12:00:00.00', 'dd-MM-yyyy HH:mm:ss.SS'));

INSERT INTO booked_seats (screening_id, row_nr, seat_nr, reservation_id) VALUES (1, 1, 2, 1);
INSERT INTO booked_seats (screening_id, row_nr, seat_nr, reservation_id) VALUES (1, 1, 3, 1);
INSERT INTO booked_seats (screening_id, row_nr, seat_nr, reservation_id) VALUES (1, 1, 4, 1);

INSERT INTO ticket_types (type_name, price, valid) VALUES ('child', 12.50, 1);
INSERT INTO ticket_types (type_name, price, valid) VALUES ('student', 18, 1);
INSERT INTO ticket_types (type_name, price, valid) VALUES ('adult', 25, 1);

--- http://localhost:8080/book/list-screenings?from=2021-05-17T12:10:52Z&to=2021-05-31T12:10:52Z

-- java -cp C:\Users\micha\.m2\repository\com\h2database\h2\1.4.200\h2-1.4.200.jar org.h2.tools.RunScript -user sa -password password -url jdbc:h2:./database/cinema -script ./da
--   tabase/schema.sql
