CREATE TABLE car (
    id SERIAL PRIMARY KEY IDENTITY,
    brand TEXT UNIQUE,
    model TEXT NOT NULL,
    price MONEY CHECK (price > 0)
);

CREATE TABLE human (
    id SERIAL PRIMARY KEY IDENTITY,
    name TEXT UNIQUE,
    age INTEGER CHECK (age >= 0) AND (age < 120),
    drivingLicense BOOLEAN DEFAULT false,
    carId INTEGER NOT NULL REFERENCES car (Id)
);

SELECT human.id, human.name, human.age, human.drivingLicense, car.brand, car.model, car.price
FROM human
LEFT JOIN car ON human.carId = car.id;