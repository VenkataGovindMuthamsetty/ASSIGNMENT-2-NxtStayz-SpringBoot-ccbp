INSERT INTO hotel (name, location, rating)
SELECT 'The Plaza Hotel', 'New York', 4
WHERE NOT EXISTS (SELECT 1 FROM hotel WHERE id = 1);

INSERT INTO hotel (name, location, rating)
SELECT 'The Beverly Hills Hotel', 'Los Angeles', 5
WHERE NOT EXISTS (SELECT 2 FROM hotel WHERE id = 2);

INSERT INTO hotel (name, location, rating)
SELECT 'The Langham', 'Chicago', 3
WHERE NOT EXISTS (SELECT 3 FROM hotel WHERE id = 3);

INSERT INTO room (roomNumber, type, price, hotelId)
SELECT 'A-101', 'Deluxe Room', 375.00, 1
WHERE NOT EXISTS (SELECT 1 FROM room WHERE id = 1);

INSERT INTO room (roomNumber, type, price, hotelId)
SELECT 'A-205', 'Suite', 950.00, 1
WHERE NOT EXISTS (SELECT 2 FROM room WHERE id = 2);

INSERT INTO room (roomNumber, type, price, hotelId)
SELECT 'B-106', 'Penthouse Suite', 2500.00, 1
WHERE NOT EXISTS (SELECT 3 FROM room WHERE id = 3);

INSERT INTO room (roomNumber, type, price, hotelId)
SELECT 'C-401', 'Superior Guest Room', 465.00, 2
WHERE NOT EXISTS (SELECT 4 FROM room WHERE id = 4);

INSERT INTO room (roomNumber, type, price, hotelId)
SELECT 'D-202', 'Bungalow', 1250.00, 2
WHERE NOT EXISTS (SELECT 5 FROM room WHERE id = 5);

INSERT INTO room (roomNumber, type, price, hotelId)
SELECT 'A-107', 'Penthouse Suite', 3300.00, 2
WHERE NOT EXISTS (SELECT 6 FROM room WHERE id = 6);

INSERT INTO room (roomNumber, type, price, hotelId)
SELECT 'A-301', 'Grand Room', 410.00, 3
WHERE NOT EXISTS (SELECT 7 FROM room WHERE id = 7);

INSERT INTO room (roomNumber, type, price, hotelId)
SELECT 'C-313', 'Executive Suite', 700.00, 3
WHERE NOT EXISTS (SELECT 8 FROM room WHERE id = 8);

INSERT INTO room (roomNumber, type, price, hotelId)
SELECT 'D-404', 'Premier Suite', 880.00, 3
WHERE NOT EXISTS (SELECT 9 FROM room WHERE id = 9);