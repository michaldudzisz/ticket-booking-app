DROP TABLE IF EXISTS movies;
DROP TABLE IF EXISTS seat_rows;
DROP TABLE IF EXISTS booked_seats;
DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS screenings;
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
  row_start INT NOT NULL,
  row_end INT NOT NULL,
  primary key (room_id, row_start),
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

CREATE TABLE customers (
  id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(50),
  last_name VARCHAR(50)
);

CREATE TABLE reservations (
  id INT AUTO_INCREMENT PRIMARY KEY,
  screening_id INT NOT NULL,
  customer_id INT NOT NULL,
  done TIMESTAMP NOT NULL,
  foreign key (screening_id) references screenings(id),
  foreign key (customer_id) references customers(id)
);

CREATE TABLE booked_seats (
  screening_id INT NOT NULL,
  nr INT NOT NULL,
  reservation_id INT,
  foreign key (screening_id) references screenings(id),
  foreign key (reservation_id) references reservations(id),
  primary key (screening_id, nr)
);

