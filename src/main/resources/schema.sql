CREATE TABLE IF NOT EXISTS hotel (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    location VARCHAR(255),
    rating INT
);

CREATE TABLE IF NOT EXISTS room (
    id INT AUTO_INCREMENT PRIMARY KEY,
    roomNumber VARCHAR(255),
    type VARCHAR(255),
    price DOUBLE,
    hotelId INT,
    FOREIGN KEY (hotelId) REFERENCES hotel(id)
);