CREATE TABLE human (
    id SERIAL,
    name TEXT PRIMARY KEY,
    age INTEGER CHECK (age >= 0) AND (age < 120),
    drivingLicense BOOLEAN,
    carId INTEGER NOT NULL
);

CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    brand TEXT UNIQUE,
    model TEXT NOT NULL,
    price MONEY CHECK (price > 0)
);

SELECT human.id, human.name, human.age, human.drivingLicense, car.brand, car.model, car.price
FROM human
LEFT JOIN car ON human.carId = car.id;