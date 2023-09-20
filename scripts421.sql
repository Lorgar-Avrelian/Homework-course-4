ALTER TABLE student
ADD CONSTRAINT age_check CHECK (age >= 16);

ALTER TABLE student
ADD CONSTRAINT name_check PRIMARY KEY (name);

ALTER TABLE faculty
ADD CONSTRAINT color_constraint UNIQUE (color);

ALTER TABLE student
ADD CONSTRAINT age_default DEFAULT '20' FOR age;