DROP TABLE IF EXISTS reservation_tickets;
DROP TABLE IF EXISTS ticket_types;
DROP TABLE IF EXISTS reservation_tickets;
DROP TABLE IF EXISTS booked_seats;
DROP TABLE IF EXISTS seat_rows;
DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS screenings;
DROP TABLE IF EXISTS movies;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS rooms;

CREATE TABLE movies (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(70) NOT NULL,
  description VARCHAR(500) DEFAULT NULL
);

CREATE TABLE rooms (
  id INT PRIMARY KEY
);

CREATE TABLE seat_rows (
  room_id INT NOT NULL,
  row_nr INT NOT NULL,
  row_length INT NOT NULL,
  primary key (room_id, row_nr),
  foreign key (room_id) references rooms(id)
);

-- TO DO validate seat rows

CREATE TABLE screenings (
  id INT AUTO_INCREMENT PRIMARY KEY,
  room_id INT NOT NULL,
  movie_id INT NOT NULL,
  date TIMESTAMP NOT NULL,
  foreign key (room_id) references rooms(id),
  foreign key (movie_id) references movies(id)
);

CREATE TABLE reservations (
  id INT AUTO_INCREMENT PRIMARY KEY,
  screening_id INT NOT NULL,
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  done TIMESTAMP NOT NULL,
  foreign key (screening_id) references screenings(id)
);

CREATE TABLE booked_seats (
  screening_id INT NOT NULL,
  row_nr INT NOT NULL,
  seat_nr INT NOT NULL,
  reservation_id INT NOT NULL,
  foreign key (screening_id) references screenings(id),
  foreign key (reservation_id) references reservations(id),
  primary key (screening_id, row_nr, seat_nr)
);

CREATE TABLE ticket_types (
  id INT AUTO_INCREMENT PRIMARY KEY,
  type_name VARCHAR(20) NOT NULL,
  price DECIMAL(4, 2) NOT NULL,
  valid INT DEFAULT 0
);

CREATE TABLE reservation_tickets (
  id INT AUTO_INCREMENT PRIMARY KEY,
  reservation_id INT NOT NULL,
  ticket_type_id INT NOT NULL,
  foreign key (reservation_id) references reservations(id),
  foreign key (ticket_type_id) references ticket_types(id)
);


-- TO DO constraint, that seat_nr <= row_nr
-- TO DO constraint, that there are no 2 same ticket
-- type_name valid at the same time